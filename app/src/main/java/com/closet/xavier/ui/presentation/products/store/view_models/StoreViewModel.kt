package com.closet.xavier.ui.presentation.products.store.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.brands.GetBrandsUseCase
import com.closet.xavier.domain.use_cases.products.GetProductsUseCase
import com.closet.xavier.domain.use_cases.products.ToggleFavoriteUseCase
import com.closet.xavier.ui.presentation.home.states.BrandsState
import com.closet.xavier.ui.presentation.home.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,

    ) : ViewModel() {
    companion object {
        private const val TAG = "StoreViewModel"
    }

    private lateinit var currentUserId:String

    private val _loggedInState = MutableStateFlow(false)
    val loggedInState = _loggedInState.asStateFlow()

    private val _userIdState = MutableStateFlow("")
    val userIdState = _userIdState.asStateFlow()


    private val _brandState = MutableStateFlow<BrandsState>(BrandsState.Loading)
    val brandsState = _brandState.asStateFlow()

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Loading)
    val productsState = _productsState.asStateFlow()


    init {
        getAuthState()
        getBrands()
        getProducts()
    }

    private fun getAuthState() {
        viewModelScope.launch {
            checkAuthStateUseCase().collect { loginState ->
                if (loginState) {
                    Log.d(TAG, "getAuthState: Logged In::$loginState")
                    _loggedInState.value = loginState
                    getCurrentUser()
                } else {
                    Log.d(TAG, "getAuthState: Logged In::$loginState")
                    _loggedInState.value = loginState
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

    private fun getBrands() {
        getBrandsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getBrands: ${result.errorResponse}")
                        _brandState.value = BrandsState.Error(error = result.errorResponse)
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getBrands: loading")
                    _brandState.value = BrandsState.Loading
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getBrands: ${result.data}")
                        _brandState.value = BrandsState.Success(brandList = result.data)
                    }
                }

                else -> {}
            }

        }.launchIn(viewModelScope)

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
                        _productsState.value = ProductsState.Success(products = result.data)
                    }
                }

                else -> {}
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