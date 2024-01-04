package com.closet.xavier.ui.presentation.products.product_details.states

import com.closet.xavier.data.firebase.model.product.Product

sealed class ProductState {
    data object Loading : ProductState()
    data class Success(val product: Product, val images: List<String>) : ProductState()
    data class Error(val errorMessage: String) : ProductState()
}
