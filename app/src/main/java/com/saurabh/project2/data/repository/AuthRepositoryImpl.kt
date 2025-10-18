package com.saurabh.project2.data.repository

import com.saurabh.project2.data.local.LocalDataSource
import com.saurabh.project2.data.model.User
import com.saurabh.project2.data.remote.FirebaseAuthDataSource
import com.saurabh.project2.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {

    override suspend fun sendOtp(phoneNumber: String): Result<String> = try {
        firebaseAuthDataSource.sendOtp(phoneNumber)
        Result.success("OTP sent successfully")
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String
    ): Result<User> = try {
        val result = firebaseAuthDataSource.verifyOtp(verificationId, otp)

        result.fold(
            onSuccess = { userId ->
                val firebaseUser = firebaseAuthDataSource.getCurrentUser()
                val user = User(
                    userId = userId,
                    phoneNumber = firebaseUser?.phoneNumber ?: ""
                )

                firebaseAuthDataSource.saveUserToFirestore(userId, user)
                localDataSource.saveUser(user)

                Result.success(user)
            },
            onFailure = { throwable ->
                Result.failure(throwable)
            }
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun resetPassword(phoneNumber: String): Result<String> =
        sendOtp(phoneNumber)

    override suspend fun updateUserProfile(user: User): Result<Boolean> = try {
        val userId = localDataSource.getUser()?.userId
            ?: return Result.failure(Exception("User not found"))

        firebaseAuthDataSource.updateUserProfile(userId, user)
            .fold(
                onSuccess = {
                    localDataSource.saveUser(user)
                    Result.success(true)
                },
                onFailure = { throwable ->
                    Result.failure(throwable)
                }
            )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCurrentUser(): Result<User?> = try {
        val user = localDataSource.getUser()
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logout(): Result<Boolean> = try {
        firebaseAuthDataSource.logout()
        localDataSource.clearUser()
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
