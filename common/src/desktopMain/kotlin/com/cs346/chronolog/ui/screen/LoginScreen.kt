package com.cs346.chronolog.ui.screen

import androidx.compose.foundation.layout.*
import com.cs346.chronolog.ui.login.LoginViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cs346.chronolog.ui.login.LoginScreen

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onSignupSuccess: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LoginScreen(
            viewModel = viewModel,
            onLogin = {
                // Handle login success logic here
                onLoginSuccess()
            },
            onSignup = {
                onSignupSuccess()
            }
        )
    }
}
