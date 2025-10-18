package com.saurabh.project2.domain.usecase

import com.saurabh.project2.domain.repository.AuthRepository

class VerifyOtpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(verificationId: String, otp: String): Result<User> {
        return if (verificationId.isEmpty()) {
            Result.Failure(Exception("Verification ID is empty"))
        } else if (otp.length != 6) {
            Result.Failure(Exception("OTP must be 6 digits"))
        } else {
            authRepository.verifyOtp(verificationId, otp)
        }
    }
}