package com.closet.xavier.ui.presentation.profile.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.GetDarkThemeUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.ToggleDarkThemeUseCase
import com.closet.xavier.domain.use_cases.user_profile.GetUserProfileUseCase
import com.closet.xavier.ui.presentation.home.states.UserProfileState
import com.closet.xavier.ui.presentation.main.state.AppThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val currentUserId = mutableStateOf("")
    private val _loggedInState = MutableStateFlow(false)
    val loggedInState = _loggedInState.asStateFlow()


    private val _userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val userProfileState = _userProfileState.asStateFlow()

    init {
        getAuthState()
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
}