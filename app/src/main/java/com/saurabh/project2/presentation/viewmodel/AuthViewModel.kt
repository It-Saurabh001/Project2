package com.saurabh.project2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurabh.project2.data.model.OtpState
import com.saurabh.project2.data.model.User
import com.saurabh.project2.domain.usecase.ResetPasswordUseCase
import com.saurabh.project2.domain.usecase.SendOtpUseCase
import com.saurabh.project2.domain.usecase.UpdateUserProfileUseCase
import com.saurabh.project2.domain.usecase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.fold

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _otpState = MutableStateFlow<OtpState>(OtpState.Idle)
    val otpState: StateFlow<OtpState> = _otpState.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _verificationId = MutableStateFlow("")
    val verificationId: StateFlow<String> = _verificationId.asStateFlow()

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun sendOtp() {
        if (_phoneNumber.value.isEmpty()) {
            _otpState.value = OtpState.Error("Phone number is required")
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _otpState.value = OtpState.Loading

            val result = sendOtpUseCase(_phoneNumber.value)

            result.fold(
                onSuccess = { verificationId ->
                    _verificationId.value = verificationId
                    _otpState.value = OtpState.OtpSent(verificationId)
                    _loading.value = false
                },
                onFailure = { exception ->
                    _otpState.value = OtpState.Error(exception.message ?: "Failed to send OTP")
                    _loading.value = false
                }
            )
        }
    }

    fun verifyOtp(otp: String) {
        if (otp.isEmpty() || otp.length < 6) {
            _otpState.value = OtpState.Error("Please enter valid 6-digit OTP")
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _otpState.value = OtpState.Loading

            val result = verifyOtpUseCase(_verificationId.value, otp)

            result.fold(
                onSuccess = { user ->
                    _userProfile.value = user
                    _otpState.value = OtpState.Verified(user)
                    _loading.value = false
                },
                onFailure = { exception ->
                    _otpState.value = OtpState.Error(exception.message ?: "OTP verification failed")
                    _loading.value = false
                }
            )
        }
    }

    fun resetPassword() {
        if (_phoneNumber.value.isEmpty()) {
            _otpState.value = OtpState.Error("Phone number is required")
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _otpState.value = OtpState.Loading

            val result = resetPasswordUseCase(_phoneNumber.value)

            result.fold(
                onSuccess = { verificationId ->
                    _verificationId.value = verificationId
                    _otpState.value = OtpState.OtpSent(verificationId)
                    _loading.value = false
                },
                onFailure = { exception ->
                    _otpState.value = OtpState.Error(exception.message ?: "Password reset failed")
                    _loading.value = false
                }
            )
        }
    }

    fun updateProfile(shopName: String, ownerName: String, address: String) {
        if (shopName.isEmpty() || ownerName.isEmpty()) {
            _otpState.value = OtpState.Error("All fields are required")
            return
        }

        viewModelScope.launch {
            _loading.value = true

            val updatedUser = (_userProfile.value ?: User()).copy(
                shopName = shopName,
                ownerName = ownerName,
                address = address
            )

            val result = updateUserProfileUseCase(updatedUser)

            result.fold(
                onSuccess = {
                    _userProfile.value = updatedUser
                    _otpState.value = OtpState.Verified(updatedUser)
                    _loading.value = false
                },
                onFailure = { exception ->
                    _otpState.value = OtpState.Error(exception.message ?: "Profile update failed")
                    _loading.value = false
                }
            )
        }
    }

    fun resetAuthState() {
        _otpState.value = OtpState.Idle
        _phoneNumber.value = ""
        _verificationId.value = ""
    }
}