package com.closet.xavier.ui.presentation.authentication.state

data class CheckUserProfileState(
    val isPresent: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
