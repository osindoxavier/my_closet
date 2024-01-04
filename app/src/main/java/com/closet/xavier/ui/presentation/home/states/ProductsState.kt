package com.closet.xavier.ui.presentation.home.states

import com.closet.xavier.data.firebase.model.product.Product

sealed class ProductsState {
    data class Success(val products: List<Product>? = emptyList()) :
        ProductsState()

    data class Error(val error: String = "") : ProductsState()
    data object Loading : ProductsState()
}
