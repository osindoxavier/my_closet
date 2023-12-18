package com.closet.xavier.data.firebase.model.base

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String?=null
)
