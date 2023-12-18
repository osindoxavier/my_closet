package com.closet.xavier.ui.presentation.home.states

import com.closet.xavier.data.firebase.model.brand.Brand

data class BrandsState(
    val brands: List<Brand>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
