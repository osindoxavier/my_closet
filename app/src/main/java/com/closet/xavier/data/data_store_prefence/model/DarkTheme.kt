package com.closet.xavier.data.data_store_prefence.model

import androidx.compose.runtime.compositionLocalOf

data class DarkTheme(var isDark: Boolean = false)

val LocalTheme = compositionLocalOf { DarkTheme() }
