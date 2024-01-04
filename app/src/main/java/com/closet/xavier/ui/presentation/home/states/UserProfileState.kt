package com.closet.xavier.ui.presentation.home.states

import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.ui.presentation.authentication.onboarding.states.CheckUserProfileState

sealed class UserProfileState {
    data object Loading : UserProfileState()
    data class Success(val profile: UserProfile? = null) : UserProfileState()
    data class Error(val errorMessage: String) : UserProfileState()
}
