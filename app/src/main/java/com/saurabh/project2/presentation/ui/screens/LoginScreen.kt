package com.saurabh.project2.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onVerificationSuccess: () -> Unit
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val otpState by viewModel.otpState.collectAsState()
    var showOtpField by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }

    LaunchedEffect(otpState) {
        if (otpState is OtpState.Verified) {
            onVerificationSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "🏗 BuildMate",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (!showOtpField) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { viewModel.setPhoneNumber(it) },
                label = { Text("Phone Number") },
                placeholder = { Text("+91XXXXXXXXXX") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    viewModel.sendOtp()
                    showOtpField = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = phoneNumber.isNotEmpty()
            ) {
                Text("Send OTP")
            }
        } else {
            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Enter OTP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = { viewModel.verifyOtp(otp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = otp.length == 6
            ) {
                Text("Verify OTP")
            }

            TextButton(onClick = { showOtpField = false }) {
                Text("Change Phone Number")
            }
        }

        when (otpState) {
            is OtpState.Loading -> CircularProgressIndicator(Modifier.padding(top = 16.dp))
            is OtpState.Error -> Text(
                (otpState as OtpState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
            else -> {}
        }

        TextButton(onClick = { /* Navigate to reset password */ }) {
            Text("Forgot Password?")
        }
    }
}