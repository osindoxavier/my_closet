package com.closet.xavier.presentation.authentication.state

data class SignInState(
    val success: String? = "",
    val error: String? = "",
    val isLoading: Boolean = false
)
