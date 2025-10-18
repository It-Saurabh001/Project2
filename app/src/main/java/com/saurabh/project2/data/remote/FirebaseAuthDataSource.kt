package com.saurabh.project2.data.remote
import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.saurabh.project2.data.model.User
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Handles all remote data operations related to Firebase Authentication and Firestore.
 *
 * @param firebaseAuth The Firebase Authentication instance.
 * @param firestore The Firebase Firestore instance.
 */
class FirebaseAuthDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    private val usersCollection = firestore.collection("users")

    /**
     * Sends an OTP code to the given phone number.
     *
     * @param phoneNumber The phone number to send the OTP to (including country code).
     * @return A [Result] containing the verification ID on success, or an exception on failure.
     */
    suspend fun sendOtp(phoneNumber: String): Result<String> = suspendCoroutine { continuation ->
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback is triggered when auto-retrieval is successful.
                // As the primary flow requires manual OTP entry, we don't need to handle this here,
                // but the verification ID is still sent in onCodeSent.
            }

            override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                continuation.resume(Result.failure(e))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                continuation.resume(Result.success(verificationId))
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            // It is highly recommended to use a reCAPTCHA verifier or a dummy Activity
            // to prevent abuse. For simplicity in this example, we pass a null activity.
            // In a real app, replace `null` with `activityContext`.
            .setActivity(null as Activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    /**
     * Verifies the OTP code and signs in the user.
     *
     * @param verificationId The ID received from the `sendOtp` call.
     * @param otp The 6-digit code entered by the user.
     * @return A [Result] containing the user's UID on success, or an exception on failure.
     */
    suspend fun verifyOtp(verificationId: String, otp: String): Result<String> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val userId = authResult.user?.uid
            if (userId != null) {
                Result.success(userId)
            } else {
                Result.failure(Exception("Authentication failed: User ID is null."))
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid OTP. Please try again."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Saves or updates user data in the Firestore "users" collection.
     *
     * @param userId The UID of the user.
     * @param user The [User] data object to save.
     */
    suspend fun saveUserToFirestore(userId: String, user: User) {
        usersCollection.document(userId).set(user).await()
    }

    /**
     * Updates a user's profile information in Firestore.
     *
     * @param userId The UID of the user to update.
     * @param user The [User] object with updated information.
     * @return A [Result] indicating success (true) or failure.
     */
    suspend fun updateUserProfile(userId: String, user: User): Result<Boolean> {
        return try {
            usersCollection.document(userId).set(user).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves the currently signed-in Firebase user.
     *
     * @return The [FirebaseUser] object or null if no user is signed in.
     */
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Signs the current user out.
     */
    fun logout() {
        firebaseAuth.signOut()
    }
}
