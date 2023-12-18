package com.closet.xavier.ui.presentation.authentication.events

sealed class AuthenticationFormEvent {
    data class EmailChanged(val email: String) : AuthenticationFormEvent()
    data class PasswordChanged(val password: String) : AuthenticationFormEvent()
    data object Submit : AuthenticationFormEvent()
}
