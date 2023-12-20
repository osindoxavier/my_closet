package com.closet.xavier.ui.presentation.authentication.onboarding.states

import com.google.firebase.auth.FirebaseUser

sealed class CurrentUserState {
    data object Loading : CurrentUserState()
    data class Success(val currentUser: FirebaseUser? = null) : CurrentUserState()
    data class Error(val errorMessage: String) : CurrentUserState()
}
