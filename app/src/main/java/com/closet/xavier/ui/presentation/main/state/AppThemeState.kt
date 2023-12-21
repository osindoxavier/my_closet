package com.closet.xavier.ui.presentation.main.state

sealed class AppThemeState {
    data object ModeAuto : AppThemeState()
    data object DarkMode : AppThemeState()
    data object LightMode : AppThemeState()
}
