package com.cs346.chronolog.ui.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun login() {
        // Add login logic here
    }

    fun signup() {
        // Add signup logic here
    }

    fun toggleMode() {
        _uiState.value = _uiState.value.copy(
            mode = if (_uiState.value.mode == LoginMode.LOGIN) LoginMode.SIGNUP else LoginMode.LOGIN
        )
    }
}
