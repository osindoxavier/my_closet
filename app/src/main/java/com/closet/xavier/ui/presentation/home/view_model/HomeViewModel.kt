package com.closet.xavier.ui.presentation.home.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.brand.Brand
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.brands.GetBrandsUseCase
import com.closet.xavier.domain.use_cases.products.AddProductToFavouriteUseCase
import com.closet.xavier.domain.use_cases.products.GetProductsUseCase
import com.closet.xavier.domain.use_cases.user_profile.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getAuthStateUseCase: CheckAuthStateUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getPopularProductsUseCase: GetProductsUseCase,
    private val addProductToFavouriteUseCase: AddProductToFavouriteUseCase
) :
    ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val currentUserId = mutableStateOf("")

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState = _errorState.asStateFlow()

    private val _userProfileState = MutableStateFlow(UserProfile())
    val userProfileState = _userProfileState.asStateFlow()

    private val _brandsState = MutableStateFlow(emptyList<Brand>())
    val brandsState = _brandsState.asStateFlow()

    private val _popularProductsState = MutableStateFlow(emptyList<Product>())
    val popularProductsState = _popularProductsState.asStateFlow()

    private val _addProductToFavouriteState = MutableStateFlow(false)
    val addProductToFavouriteState = _addProductToFavouriteState.asStateFlow()

    init {
        getAuthState()
        getBrands()
        getPopularProducts()
    }


    private fun getAuthState() {
        viewModelScope.launch {
//            getAuthStateUseCase(viewScope = viewModelScope).collect { user ->
//                if (user != null) {
//                    Log.d(TAG, "getAuthState: $user")
//                    currentUserId.value = user.uid
//                    getUserProfile(userId = user.uid)
//                }
//            }
        }
    }

    private fun getUserProfile(userId: String) {
        getUserProfileUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getUserProfile: ${result.errorResponse}")
                        _errorState.value = result.errorResponse
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getUserProfile: loading")
                    _loadingState.value = true
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getUserProfile: ${result.data}")
                        _userProfileState.value = result.data
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getBrands() {
        getBrandsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getBrands: ${result.errorResponse}")
                        _errorState.value = result.errorResponse
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getBrands: loading")
                    _loadingState.value = true
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getBrands: ${result.data}")
                        _brandsState.value = result.data
                    }
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun getPopularProducts() {
        getPopularProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getPopularProducts: ${result.errorResponse}")
                        _errorState.value = result.errorResponse
                    }
                }

                is Resource.Loading -> {
                    _loadingState.value = true
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getPopularProducts: ${result.data}")
                        _popularProductsState.value = result.data.filter { it.popular }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun addFavoriteProduct(productId: String) {
        addProductToFavouriteUseCase(
            productId = productId,
            userId = currentUserId.value
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "addFavoriteProduct: ${result.errorResponse}")
                        _errorState.value = result.errorResponse
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "addFavoriteProduct: loading")
                }

                is Resource.Success -> {
                    if (result.data == true) {
                        Log.d(TAG, "addFavoriteProduct: product added or removed")
                        _addProductToFavouriteState.value = result.data
                        getPopularProducts()
                    }
                }
            }

        }.launchIn(viewModelScope)

    }
}