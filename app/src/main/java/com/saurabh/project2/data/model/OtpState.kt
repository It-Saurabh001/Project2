package com.saurabh.project2.data.model

sealed class OtpState {
    object Idle : OtpState()
    object Loading : OtpState()
    data class OtpSent(val verificationId: String) : OtpState()
    data class Verified(val user: User) : OtpState()
    data class Error(val message: String) : OtpState()
}