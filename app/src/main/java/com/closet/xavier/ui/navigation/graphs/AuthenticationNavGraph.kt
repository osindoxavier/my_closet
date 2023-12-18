package com.closet.xavier.ui.navigation.graphs

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.closet.xavier.domain.use_cases.internet.ConnectionLiveDataUseCase
import com.closet.xavier.ui.navigation.models.AuthenticationNavItem
import com.closet.xavier.ui.navigation.models.BottomNavItem
import com.closet.xavier.ui.navigation.models.RootNavItem
import com.closet.xavier.ui.presentation.authentication.screen.OnBoardingScreen
import com.closet.xavier.ui.presentation.authentication.screen.SignInScreen
import com.closet.xavier.ui.presentation.authentication.screen.SignUpScreen
import com.closet.xavier.ui.presentation.authentication.view_model.AuthStateViewModel
import com.closet.xavier.ui.presentation.authentication.view_model.SignInViewModel
import com.closet.xavier.ui.presentation.authentication.view_model.OnBoardingViewModel
import com.closet.xavier.ui.presentation.authentication.view_model.SignUpViewModel

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(
        startDestination = AuthenticationNavItem.OnboardingScreen.route,
        route = RootNavItem.Authentication.id.toString()
    ) {

        composable(route = AuthenticationNavItem.OnboardingScreen.route) {
            val TAG = "OnBoardingScreen"
            val viewModel: OnBoardingViewModel = hiltViewModel()
            val authStateViewModel: AuthStateViewModel = hiltViewModel()
            val onSignInNavigation = {
                navController.navigate(route = AuthenticationNavItem.SignInScreen.route)
            }

            val onSaveNewUserStatus = {
                viewModel.saveFirstTimeStatus()
            }
            val state = viewModel.stateIsFirstTimeUser.collectAsStateWithLifecycle()
            val currentUser = authStateViewModel.getAuthState().collectAsStateWithLifecycle().value

            val onSignUpNavigation = {
                navController.navigate(AuthenticationNavItem.SignUpScreen.route)
            }

            val onHomeNavigation = {
                navController.navigate(BottomNavItem.Home.route)
            }

            val context = LocalContext.current

            val isLoading = remember {
                mutableStateOf(false)
            }


            LaunchedEffect(key1 = authStateViewModel.checkUserProfileState, block = {
                authStateViewModel.checkUserProfileState.collect { result ->
                    isLoading.value = result.isLoading
                    if (result.errorMessage.isNotEmpty()) {
                        Log.e(TAG, "OnBoardingScreen: ${result.errorMessage}")
                    }
                    if (result.isPresent) {
                        Log.d(TAG, "OnBoardingScreen: User Profile Present= ${result.isPresent}")
                        onHomeNavigation()
                    } else {
                        Log.e(TAG, "OnBoardingScreen: User Profile is not present!Create one!")
                        Toast.makeText(
                            context,
                            "User Profile is not available. Create User Profile!",
                            Toast.LENGTH_SHORT
                        ).show()
                        onSignUpNavigation()
                    }
                }
            })

            OnBoardingScreen(
                onSignInNavigation = onSignInNavigation,
                saveNewUserStatus = onSaveNewUserStatus,
                state = state,
                currentUser = currentUser,
                onSignUpNavigation = onSignUpNavigation,
                authStateViewModel = authStateViewModel,
                isLoading = isLoading
            )
        }

        composable(route = AuthenticationNavItem.SignInScreen.route) {
            val TAG = "SignInScreen"
            //context
            val context = LocalContext.current
            //connectionLiveData
            val connectionLiveDataUseCase = ConnectionLiveDataUseCase(context)
            val networkState = connectionLiveDataUseCase.observeAsState(false)
            //view model
            val viewModel: SignInViewModel = hiltViewModel()
            val onSignUpClick = {
                navController.navigate(AuthenticationNavItem.SignUpScreen.route)
            }
            LaunchedEffect(key1 = viewModel.checkUserProfileState, block = {
                viewModel.checkUserProfileState.collect { result ->
                    if (result.errorMessage.isNotEmpty()) {
                        Log.e(TAG, "authenticationGraph: ${result.errorMessage}")
                    }
                    if (result.isPresent) {
                        Log.d(TAG, "authenticationGraph: ${result.isPresent}")
                    }

                }
            })
            SignInScreen(
                networkState = networkState,
                viewModel = viewModel,
                context = context,
                onSignUpClick = onSignUpClick
            )
        }

        composable(route = AuthenticationNavItem.SignUpScreen.route) {
            //context
            val context = LocalContext.current
            //connectionLiveData
            val connectionLiveDataUseCase = ConnectionLiveDataUseCase(context)
            val networkState = connectionLiveDataUseCase.observeAsState(false)
            //view model
            val viewModel: SignUpViewModel = hiltViewModel()
            val onSignInClicked = {
                navController.navigate(AuthenticationNavItem.SignInScreen.route)
            }
            //auth listener
            val authStateViewModel: AuthStateViewModel = hiltViewModel()
            val currentUser = authStateViewModel.getAuthState().collectAsStateWithLifecycle().value
            SignUpScreen(
                context = context,
                networkState = networkState,
                viewModel = viewModel,
                onSignInClicked = onSignInClicked,
                currentUser = currentUser
            )
        }
    }
}