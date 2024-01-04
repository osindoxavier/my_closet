package com.closet.xavier.ui.presentation.main.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.domain.use_cases.data_store_preferences.GetDarkThemeUseCase
import com.closet.xavier.domain.use_cases.data_store_preferences.ToggleDarkThemeUseCase
import com.closet.xavier.ui.presentation.main.state.AppThemeState
import com.closet.xavier.ui.presentation.profile.view_model.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val toggleDarkThemeUseCase: ToggleDarkThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
) : ViewModel() {
    companion object {
        private const val TAG = "ThemeViewModel"
    }

    private val _themeState = MutableStateFlow<AppThemeState>(AppThemeState.ModeAuto)
    val themeState = _themeState.asStateFlow()

    init {
        getTheme()
    }

    private fun getTheme() {
        viewModelScope.launch {
            getDarkThemeUseCase().collect {
                Log.d(TAG, "getTheme: idDarkMode::$it")
                if (it.isDark) {
                    _themeState.value = AppThemeState.DarkMode
                } else {
                    _themeState.value = AppThemeState.LightMode
                }
            }
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            toggleDarkThemeUseCase(isDark = isDark)
            getTheme()
        }
    }
}