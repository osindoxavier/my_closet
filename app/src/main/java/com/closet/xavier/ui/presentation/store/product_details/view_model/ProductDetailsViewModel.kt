package com.closet.xavier.ui.presentation.store.product_details.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.domain.use_cases.products.GetProductByIdUseCase
import com.closet.xavier.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProductDetailsViewModel"
    }

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState = _errorState.asStateFlow()

    private val _getProductByIdState = MutableStateFlow(Product())
    val getProductByIdState = _getProductByIdState.asStateFlow()

    private val _productImagesState = MutableStateFlow(emptyList<String>())
    val productImagesState = _productImagesState.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PRODUCT_ID)?.let { productId ->
            Log.d(TAG, "init block: $productId")
            getProductById(productId = productId)
        }
    }


    private fun getProductById(productId: String) {
        getProductByIdUseCase(productId = productId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getProductById: ${result.errorResponse}")
                        _errorState.value = result.errorResponse
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getProductById: loading")
                    _loadingState.value = true
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getProductById: ${result.data}")
                        _getProductByIdState.value = result.data
                        val thumbs = result.data.thumb as? List<String>
                        getProductImages(result.data.image, thumbs)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getProductImages(image: String, thumbs: List<String>?) {
        val list = mutableListOf<String>()
        list.add(image)
        if (thumbs != null) {
            for (thumb in thumbs) {
                list.add(thumb)
            }
        }
        _productImagesState.value = list
    }


}