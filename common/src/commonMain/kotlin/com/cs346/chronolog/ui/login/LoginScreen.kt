package com.cs346.chronolog.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cs346.chronolog.data.getImageByFileName
import com.cs346.chronolog.data.painterResource

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(getImageByFileName("ic_launcher")),
            contentDescription = "Chronolog Logo",
        )
        OutlinedTextField(
            value = uiState.username,
            onValueChange = viewModel::updateUsername,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        if (uiState.mode == LoginMode.SIGNUP) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::updateConfirmPassword,
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.mode == LoginMode.LOGIN) {
            Button(
                onClick = {
                    viewModel.login()
                    onLogin()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Login")
            }
        } else {
            Button(
                onClick = {
                    viewModel.signup()
                    onSignup()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Signup")
            }
        }
        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(uiState.errorMessage!!, color = androidx.compose.ui.graphics.Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.mode == LoginMode.LOGIN) {
            Button(
                onClick = {
                    viewModel.toggleMode()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Signup")
            }
        } else {
            Button(
                onClick = {
                    viewModel.toggleMode()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Login")
            }
        }
    }
}

