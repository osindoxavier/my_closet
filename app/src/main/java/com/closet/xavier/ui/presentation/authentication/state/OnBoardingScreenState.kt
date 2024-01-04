package com.closet.xavier.ui.presentation.authentication.state

import com.google.firebase.auth.FirebaseUser

sealed class OnBoardingScreenState {
    data object Loading : OnBoardingScreenState()

    data class Success(
        val currentUser: FirebaseUser? = null,
        val hasProfile: Boolean = false
    ) : OnBoardingScreenState()

    data class Error(val errorMessage: String) : OnBoardingScreenState()
}
