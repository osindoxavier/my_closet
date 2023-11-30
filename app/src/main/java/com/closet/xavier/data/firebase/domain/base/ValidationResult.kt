package com.closet.xavier.data.firebase.domain.base

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String?=null
)
