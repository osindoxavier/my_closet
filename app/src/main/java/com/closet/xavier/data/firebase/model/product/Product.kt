package com.closet.xavier.data.firebase.model.product

data class Product(
    val brandId: String = "",
    val favorites: Any ?= null,
    val productId: String = "",
    val image: String = "",
    val name: String = "",
    val popular: Boolean = false,
    val price: String = "",
    val thumb: Any ?= null
)
