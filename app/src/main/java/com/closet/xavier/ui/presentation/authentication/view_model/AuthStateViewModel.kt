package com.closet.xavier.ui.presentation.authentication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.user_profile.CheckUserProfileUseCase
import com.closet.xavier.ui.presentation.authentication.state.CheckUserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AuthStateViewModel @Inject constructor(
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
    private val checkUserProfileUseCase: CheckUserProfileUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "AuthStateViewModel"
    }

    private val _checkUserProfileState = MutableSharedFlow<CheckUserProfileState>()
    val checkUserProfileState = _checkUserProfileState.asSharedFlow()

    init {
        getAuthState()
    }

    fun getAuthState() = checkAuthStateUseCase(viewScope = viewModelScope)

    fun checkUserProfile(userId: String) {
        checkUserProfileUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _checkUserProfileState.emit(CheckUserProfileState(errorMessage = result.errorResponse!!))
                }

                is Resource.Loading -> {
                    _checkUserProfileState.emit(CheckUserProfileState(isPresent = true))
                }

                is Resource.Success -> {
                    _checkUserProfileState.emit(CheckUserProfileState(isPresent = result.data!!))
                }
            }
        }.launchIn(viewModelScope)
    }
}