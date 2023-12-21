package com.closet.xavier.ui.presentation.home.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.brands.GetBrandsUseCase
import com.closet.xavier.domain.use_cases.products.AddProductToFavouriteUseCase
import com.closet.xavier.domain.use_cases.products.GetProductsUseCase
import com.closet.xavier.domain.use_cases.user_profile.GetUserProfileUseCase
import com.closet.xavier.ui.presentation.home.states.BrandsState
import com.closet.xavier.ui.presentation.home.states.ProductsState
import com.closet.xavier.ui.presentation.home.states.UserProfileState
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
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getPopularProductsUseCase: GetProductsUseCase,
    private val addProductToFavouriteUseCase: AddProductToFavouriteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) :
    ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val currentUserId = mutableStateOf("")

    private val _loggedInState = MutableStateFlow(false)
    val loggedInState = _loggedInState.asStateFlow()


    private val _userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val userProfileState = _userProfileState.asStateFlow()

    private val _brandState = MutableStateFlow<BrandsState>(BrandsState.Loading)
    val brandsState = _brandState.asStateFlow()

    private val _popularProductsState = MutableStateFlow<ProductsState>(ProductsState.Loading)
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
                getUserProfile(userId = response.uid)
            }
        }
    }

    private fun getUserProfile(userId: String) {
        getUserProfileUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getUserProfile: ${result.errorResponse}")
                        _userProfileState.value =
                            UserProfileState.Error(errorMessage = result.errorResponse)
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "getUserProfile: loading")
                    _userProfileState.value = UserProfileState.Loading
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getUserProfile: ${result.data}")
                        _userProfileState.value = UserProfileState.Success(profile = result.data)
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
            }

        }.launchIn(viewModelScope)

    }

    private fun getPopularProducts() {
        getPopularProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    if (result.errorResponse != null) {
                        Log.e(TAG, "getPopularProducts: ${result.errorResponse}")
                        _popularProductsState.value =
                            ProductsState.Error(error = result.errorResponse)
                    }
                }

                is Resource.Loading -> {
                    _popularProductsState.value = ProductsState.Loading
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        Log.d(TAG, "getPopularProducts: ${result.data}")
                        _popularProductsState.value =
                            ProductsState.Success(products = result.data.filter { it.popular })
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
//                        _errorState.value = result.errorResponse
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