package com.closet.xavier.ui.presentation.home.states

import com.closet.xavier.data.firebase.model.brand.Brand

sealed class BrandsState {
    data class Success(val brandList: List<Brand>? = emptyList()) : BrandsState()
    data class Error(val error: String = "") : BrandsState()
    data object Loading : BrandsState()


}