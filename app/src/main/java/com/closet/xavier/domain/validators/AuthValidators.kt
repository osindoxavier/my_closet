package com.closet.xavier.domain.validators

import com.closet.xavier.data.firebase.model.base.ValidationResult

interface AuthValidators {
    fun executePhone(phone: String): ValidationResult
    fun executePassword(password: String): ValidationResult
    fun executeEmail(email: String): ValidationResult
    fun executeFirstName(firstName: String): ValidationResult
    fun executeMiddleName(middleName: String): ValidationResult
    fun executeLastName(lastName: String): ValidationResult
}