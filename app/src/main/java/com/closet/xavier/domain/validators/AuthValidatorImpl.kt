package com.closet.xavier.domain.validators

import android.util.Patterns
import com.closet.xavier.data.firebase.domain.base.ValidationResult
import com.closet.xavier.utils.Constants

class AuthValidatorImpl:AuthValidators {
    override fun executePhone(phone: String): ValidationResult {
        TODO("Not yet implemented")
    }

    override fun executePassword(password: String): ValidationResult {
        if (password.isBlank()) {

            return ValidationResult(
                successful = false,
                errorMessage = "Password cannot be empty!"
            )


        }

        if (password.length < Constants.PASSWORD_TEXT_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Password!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executeConfirmPassword(
        password: String,
        repeatedPassword: String
    ): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executeEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid email address!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executeFirstName(firstName: String): ValidationResult {
        if (firstName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "First Name can't be blank!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executeMiddleName(middleName: String): ValidationResult {
        if (middleName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Middle Name can't be blank!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun executeLastName(lastName: String): ValidationResult {
        if (lastName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Last Name can't be blank!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}