package com.closet.xavier.ui.presentation.authentication.state

data class CreateProfileState(
    val success: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false
)
