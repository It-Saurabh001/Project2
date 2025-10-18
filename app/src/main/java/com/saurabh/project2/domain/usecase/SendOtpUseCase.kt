package com.saurabh.project2.domain.usecase

import com.saurabh.project2.domain.repository.AuthRepository

class SendOtpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String): Result<String> {
        return if (phoneNumber.isEmpty()) {
            Result.Failure(Exception("Phone number cannot be empty"))
        } else if (phoneNumber.length < 10) {
            Result.Failure(Exception("Invalid phone number"))
        } else {
            authRepository.sendOtp(phoneNumber)
        }
    }
}