package com.saurabh.project2.domain.usecase

import com.saurabh.project2.domain.repository.AuthRepository

class ResetPasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String): Result<String> {
        return if (phoneNumber.isEmpty()) {
            Result.Failure(Exception("Phone number cannot be empty"))
        } else {
            authRepository.resetPassword(phoneNumber)
        }
    }
}