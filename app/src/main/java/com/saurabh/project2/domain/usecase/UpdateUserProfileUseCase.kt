package com.saurabh.project2.domain.usecase

import com.saurabh.project2.data.model.User
import com.saurabh.project2.domain.repository.AuthRepository

class UpdateUserProfileUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(user: User): Result<Boolean> {
        return if (user.userId.isEmpty()) {
            Result.Failure(Exception("User ID is empty"))
        } else if (user.shopName.isEmpty()) {
            Result.Failure(Exception("Shop name is required"))
        } else {
            authRepository.updateUserProfile(user)
        }
    }
}