package com.closet.xavier.ui.presentation.authentication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.domain.use_cases.data_store_preferences.CheckIfUserIsFirstTimerUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.SaveFirstUserStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val checkIfUserIsFirstTimerUseCase: CheckIfUserIsFirstTimerUseCase,
    private val saveFirstUserStatusUseCase: SaveFirstUserStatusUseCase
) : ViewModel() {

    private val _stateIsFirstTimeUser = MutableStateFlow(true)
    val stateIsFirstTimeUser = _stateIsFirstTimeUser.asStateFlow()

    companion object {
        private const val TAG = "OnBoardingViewModel"
    }

    init {
        checkIfUserIsFirstTimeUser()
    }

    private fun checkIfUserIsFirstTimeUser() {
        viewModelScope.launch {
            checkIfUserIsFirstTimerUseCase().collect {
                _stateIsFirstTimeUser.value = it
            }
        }
    }

    fun saveFirstTimeStatus() {
        viewModelScope.launch {
            saveFirstUserStatusUseCase()
        }
    }


}