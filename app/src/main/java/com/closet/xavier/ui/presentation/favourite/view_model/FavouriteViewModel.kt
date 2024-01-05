package com.closet.xavier.ui.presentation.favourite.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.products.GetProductsUseCase
import com.closet.xavier.domain.use_cases.products.ToggleFavoriteUseCase
import com.closet.xavier.ui.presentation.home.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    private lateinit var currentUserId: String


    private val _userIdState = MutableStateFlow("")
    val userIdState = _userIdState.asStateFlow()

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Loading)
    val productsState = _productsState.asStateFlow()

    private val _addProductToFavouriteState = MutableStateFlow(false)
    val addProductToFavouriteState = _addProductToFavouriteState.asStateFlow()

    init {
        getAuthState()
        getProducts()
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
                _userIdState.value = response.uid
            }
        }
    }

    private fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getProducts: ${result.errorResponse}")
                        _productsState.value = ProductsState.Error(error = result.errorResponse)
                    }
                }

                is Resource.Loading -> {
                    _productsState.value = ProductsState.Loading
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getProducts: ${result.data}")
                        val favoritesProducts =
                            result.data.filter { it.favorites?.any { favorite -> favorite == currentUserId } == true }
                        _productsState.value =
                            ProductsState.Success(products = favoritesProducts)
                    }
                }

            }

        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(
                productId = product.productId,
                currentUserId = currentUserId
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