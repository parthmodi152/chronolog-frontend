package com.cs346.chronolog.ui.login

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mode: LoginMode = LoginMode.LOGIN
)

enum class LoginMode {
    LOGIN,
    SIGNUP
}
