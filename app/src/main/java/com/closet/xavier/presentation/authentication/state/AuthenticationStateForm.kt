package com.closet.xavier.presentation.authentication.state

data class AuthenticationStateForm(
    var phone: String = "",
    var phoneError: String? = null,
    var email: String = "",
    var emailError: String? = null,
    var firstName: String = "",
    var firstNameError: String? = null,
    var middleName: String = "",
    var middleNameError: String? = null,
    var lastName: String = "",
    var lastNameError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var confirmPassword: String = "",
    var confirmPasswordError: String? = null
)
