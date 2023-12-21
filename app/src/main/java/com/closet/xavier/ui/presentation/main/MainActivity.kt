package com.closet.xavier.ui.presentation.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import com.closet.xavier.domain.use_cases.internet.ConnectivityManager
import com.closet.xavier.ui.navigation.graphs.RootNavGraph
import com.closet.xavier.ui.presentation.main.state.AppThemeState
import com.closet.xavier.ui.presentation.main.view_model.ThemeViewModel
import com.closet.xavier.ui.presentation.theme.MyClosetTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
        )
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ThemeViewModel = hiltViewModel()
            val themeState = viewModel.themeState.collectAsState().value
            var darkTheme by remember { mutableStateOf(false) }
            when (themeState) {
                is AppThemeState.DarkMode -> {
                    DarkTheme(isDark = true)
                    darkTheme = true
                }

                is AppThemeState.LightMode -> {
                    DarkTheme(isDark = false)
                    darkTheme = false
                }

                is AppThemeState.ModeAuto -> {
                    DarkTheme(isDark = isSystemInDarkTheme())
                    darkTheme = isSystemInDarkTheme()
                }
            }

            val onThemeUpdated = {
                viewModel.toggleTheme(isDark = !darkTheme)
            }


            MyClosetTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    RootNavGraph(
                        navController = navController,
                        onThemeUpdated = onThemeUpdated,
                        themeState = themeState
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }
}