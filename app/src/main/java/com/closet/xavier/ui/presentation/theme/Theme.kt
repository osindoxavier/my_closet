package com.closet.xavier.ui.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = myClosetPrimaryDark,
    onPrimary = myClosetOnPrimaryDark,
    error = myClosetError,
    onError = myClosetOnError,
    errorContainer = myClosetErrorContainer,
    onErrorContainer = myClosetOnErrorContainer,
    surface = myClosetSurfaceDark,
    onSurface = myClosetOnSurfaceDark,
    background = myClosetBackgroundDark,
    onBackground = myClosetOnBackgroundDark
)

val LightColorScheme = lightColorScheme(
    primary = myClosetPrimaryLight,
    onPrimary = myClosetOnPrimaryLight,
    error = myClosetError,
    onError = myClosetOnError,
    errorContainer = myClosetErrorContainer,
    onErrorContainer = myClosetOnErrorContainer,
    surface = myClosetSurfaceLight,
    onSurface = myClosetOnSurfaceLight,
    background = myClosetBackgroundLight,
    onBackground = myClosetOnBackgroundLight,
)

@Composable
fun MyClosetTheme(
    darkTheme: Boolean= isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
////        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
////            val context = LocalContext.current
////            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
////        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
    val myClosetColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = myClosetColorScheme,
        typography = Typography,
        content = content
    )
}