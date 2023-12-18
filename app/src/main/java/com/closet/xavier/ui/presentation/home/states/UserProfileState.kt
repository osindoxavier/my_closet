package com.closet.xavier.ui.presentation.home.states

import com.closet.xavier.data.firebase.model.authentication.UserProfile

data class UserProfileState(
    val profile: UserProfile? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
