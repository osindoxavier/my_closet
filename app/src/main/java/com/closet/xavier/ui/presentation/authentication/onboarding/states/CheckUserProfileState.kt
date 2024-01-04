package com.closet.xavier.ui.presentation.authentication.onboarding.states

sealed class CheckUserProfileState {
    data object Loading : CheckUserProfileState()
    data class Success(val isProfilePresent: Boolean = false) : CheckUserProfileState()
    data class Error(val errorMessage: String) : CheckUserProfileState()
}
