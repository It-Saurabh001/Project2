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
fun ProfileSetupScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    imageUploadViewModel: ImageUploadViewModel = hiltViewModel(),
    onProfileComplete: () -> Unit
) {
    var shopName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val uploadState by imageUploadViewModel.uploadState.collectAsState()
    var profileImageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Complete Your Profile",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Owner Name (आपका नाम)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = shopName,
            onValueChange = { shopName = it },
            label = { Text("Shop Name (दुकान का नाम)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Shop Address (पता)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            minLines = 3
        )

        Button(
            onClick = {
                viewModel.updateProfile(shopName, ownerName, address, profileImageUrl)
                onProfileComplete()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = shopName.isNotEmpty() && ownerName.isNotEmpty() && address.isNotEmpty()
        ) {
            Text("Complete Setup (सेटअप पूरा करें)")
        }

        when (uploadState) {
            is ImageUploadViewModel.UploadState.Loading -> {
                CircularProgressIndicator(Modifier.padding(top = 16.dp))
            }
            is ImageUploadViewModel.UploadState.Success -> {
                Text("✓ Profile updated!", color = MaterialTheme.colorScheme.primary)
            }
            is ImageUploadViewModel.UploadState.Error -> {
                Text(
                    (uploadState as ImageUploadViewModel.UploadState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {}
        }
    }
}