package com.closet.xavier.ui.presentation.products.product_details.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.brands.GetBrandsUseCase
import com.closet.xavier.domain.use_cases.products.GetProductByIdUseCase
import com.closet.xavier.domain.use_cases.products.ToggleFavoriteUseCase
import com.closet.xavier.ui.presentation.home.view_model.HomeViewModel
import com.closet.xavier.ui.presentation.products.product_details.states.ProductState
import com.closet.xavier.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "ProductDetailsViewModel"
    }


    private val _getProductByIdState = MutableStateFlow<ProductState>(ProductState.Loading)
    val getProductByIdState = _getProductByIdState.asStateFlow()

    private val _currentUserIdState = MutableStateFlow("")
    val currentUserIdState = _currentUserIdState.asStateFlow()

    private lateinit var currentUserId: String

    init {
        savedStateHandle.get<String>(Constants.PRODUCT_ID)?.let { productId ->
            Log.d(TAG, "init block: $productId")
            getProductById(productId = productId)
        }
        getAuthState()
    }

    private fun getAuthState() {
        viewModelScope.launch {
            checkAuthStateUseCase().collect { loginState ->
                if (loginState) {
                    Log.d(TAG, "getAuthState: Logged In::$loginState")
                    getCurrentUser()
                } else {
                    Log.d(TAG, "getAuthState: Logged In::$loginState")
                }
            }
        }
    }


    private fun getCurrentUser() {
        viewModelScope.launch {
            val response = getCurrentUserUseCase()
            if (response != null) {
                Log.d(TAG, "getCurrentUser: ${response.uid}")
                currentUserId = response.uid
                _currentUserIdState.value = response.uid
            }
        }
    }


    private fun getProductById(productId: String) {
        getProductByIdUseCase(productId = productId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getProductById: ${result.errorResponse}")
                        _getProductByIdState.value =
                            ProductState.Error(errorMessage = result.errorResponse)
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getProductById: loading")
                    _getProductByIdState.value = ProductState.Loading
                }

                is Resource.Success -> {
                    val product = result.data
                    if (product != null) {
                        Log.d(TAG, "getProductById: $product")
                        val list = mutableListOf<String>()
                        list.add(product.image)
                        if (product.thumb?.isNotEmpty() == true) {
                            for (image in product.thumb) {
                                list.add(image)
                            }
                        }
                        _getProductByIdState.value =
                            ProductState.Success(product = product, images = list)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(
                productId = product.productId, currentUserId = currentUserId
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        if (result.errorResponse != null) {
                            Log.e(TAG, "onFavoriteClick: ${result.errorResponse}")
                        }
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "addFavoriteProduct: loading")
                    }

                    is Resource.Success -> {
                        if (result.data == true) {
                            Log.d(TAG, "onFavoriteClick: product favourite added or removed")
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
    }


}