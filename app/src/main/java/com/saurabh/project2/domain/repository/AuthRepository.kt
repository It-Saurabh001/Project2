package com.saurabh.project2.domain.repository

import com.saurabh.project2.data.model.User

interface AuthRepository {
    suspend fun sendOtp(phoneNumber: String): Result<String>
    suspend fun verifyOtp(verificationId: String, otp: String): Result<User>
    suspend fun resetPassword(phoneNumber: String): Result<String>
    suspend fun updateUserProfile(user: User): Result<Boolean>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun logout(): Result<Boolean>
}