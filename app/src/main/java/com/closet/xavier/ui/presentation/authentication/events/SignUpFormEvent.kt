package com.closet.xavier.ui.presentation.authentication.events

sealed class SignUpFormEvent {
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class PhoneChanged(val phone: String) : SignUpFormEvent()
    data class FirstNameChanged(val firstName: String) : SignUpFormEvent()
    data class MiddleNameChanged(val middleName: String) : SignUpFormEvent()
    data class LastNameChanged(val lastName: String) : SignUpFormEvent()
    data object Submit : SignUpFormEvent()
}
