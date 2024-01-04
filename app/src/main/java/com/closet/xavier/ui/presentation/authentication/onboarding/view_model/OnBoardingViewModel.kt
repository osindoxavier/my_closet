package com.closet.xavier.ui.presentation.authentication.onboarding.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.GetCurrentUserUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.CheckIfUserIsFirstTimerUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.SaveFirstUserStatusUseCase
import com.closet.xavier.domain.use_cases.user_profile.CheckUserProfileUseCase
import com.closet.xavier.ui.presentation.authentication.onboarding.states.CheckUserProfileState
import com.closet.xavier.ui.presentation.authentication.onboarding.states.CurrentUserState
import com.closet.xavier.ui.presentation.authentication.state.OnBoardingScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val checkIfUserIsFirstTimerUseCase: CheckIfUserIsFirstTimerUseCase,
    private val saveFirstUserStatusUseCase: SaveFirstUserStatusUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val checkUserProfileUseCase: CheckUserProfileUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _loggedInState = MutableStateFlow(false)
    val loggedInState = _loggedInState.asStateFlow()

    private val _newUserState = MutableStateFlow(false)
    val newUserState = _newUserState.asStateFlow()

    private val _currentUserState = MutableStateFlow<CurrentUserState>(CurrentUserState.Loading)
    val currentUserState = _currentUserState.asStateFlow()

    private val _checkUserProfileState = MutableStateFlow<CheckUserProfileState>(CheckUserProfileState.Loading)
    val checkUserProfileState = _checkUserProfileState.asStateFlow()


    companion object {
        private const val TAG = "OnBoardingViewModel"
    }

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
                    checkIfUserIsFirstTimeUser()
                }
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val response = getCurrentUserUseCase()
            if (response != null) {
                Log.d(TAG, "getCurrentUser: ${response.uid}")
                _currentUserState.value = CurrentUserState.Success(currentUser = response)
                checkUserProfile(userId = response.uid)
            }


        }
    }


    private fun checkIfUserIsFirstTimeUser() {
        viewModelScope.launch {
            checkIfUserIsFirstTimerUseCase().collect {
                Log.d(TAG, "isNewUser: $it")
                _newUserState.value = it
            }
        }
    }

    fun saveFirstTimeStatus() {
        viewModelScope.launch {
            saveFirstUserStatusUseCase()
        }
    }


    private fun checkUserProfile(userId: String) {
        checkUserProfileUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    val message = result.errorResponse
                    if (!message.isNullOrBlank()) {
                        Log.e(TAG, "checkUserProfile: $message")
                        _checkUserProfileState.value = CheckUserProfileState.Error(errorMessage = message)
                    }
                }

                is Resource.Loading -> {
                    _checkUserProfileState.value = CheckUserProfileState.Loading
                }

                is Resource.Success -> {
                    val isPresent = result.data
                    if (isPresent == true) {
                        Log.d(TAG, "checkUserProfile: isPresent::${result.data}")
                        _checkUserProfileState.value=CheckUserProfileState.Success(isPresent)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


}