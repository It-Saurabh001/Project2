package com.saurabh.project2.data.model

sealed class AuthState {
    object Uninitialized : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val exception: Exception) : AuthState()
}