package com.closet.xavier.ui.presentation.authentication.state

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

data class SignInState(
    val success: FirebaseUser? = null,
    val error: String? = "",
    val isLoading: Boolean = false
)
