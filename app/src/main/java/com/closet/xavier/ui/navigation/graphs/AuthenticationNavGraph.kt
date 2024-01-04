package com.closet.xavier.ui.navigation.graphs

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.closet.xavier.ui.presentation.authentication.onboarding.screen.OnBoardingScreen
import com.closet.xavier.ui.presentation.authentication.screen.SignInScreen
import com.closet.xavier.ui.presentation.authentication.screen.SignUpScreen
import com.closet.xavier.ui.presentation.authentication.view_model.AuthStateViewModel
import com.closet.xavier.ui.presentation.authentication.view_model.SignInViewModel
import com.closet.xavier.ui.presentation.authentication.onboarding.view_model.OnBoardingViewModel
import com.closet.xavier.ui.presentation.authentication.view_model.SignUpViewModel

fun NavGraphBuilder.authenticationGraph(navController: NavController) {
    navigation(
        startDestination = AuthenticationNavItem.OnboardingScreen.route,
        route = RootNavItem.Authentication.id.toString()
    ) {

        composable(route = AuthenticationNavItem.OnboardingScreen.route) {
            val TAG = "OnBoardingScreen"
            val viewModel: OnBoardingViewModel = hiltViewModel()
            val userProfileState =
                viewModel.checkUserProfileState.collectAsStateWithLifecycle().value
            val currentUserState = viewModel.currentUserState.collectAsStateWithLifecycle().value
            val loggedInState = viewModel.loggedInState.collectAsStateWithLifecycle()
            val newUserState = viewModel.newUserState.collectAsStateWithLifecycle()


            val navigateToHomeScreen = {
                navController.navigate(BottomNavItem.Home.route)
            }

            val navigateToSignInScreen = {
                navController.navigate(AuthenticationNavItem.SignInScreen.route)
            }

            val navigateToSignUpScreen = {
                navController.navigate(AuthenticationNavItem.SignUpScreen.route)
            }




            OnBoardingScreen(
                userProfileState = userProfileState,
                currentUserState = currentUserState,
                loggedInState = loggedInState,
                newUserState = newUserState,
                navigateToHomeScreen = navigateToHomeScreen,
                navigateToSignInScreen = navigateToSignInScreen,
                navigateToSignUpScreen=navigateToSignUpScreen
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
//            val currentUser = authStateViewModel.getAuthState().collectAsStateWithLifecycle().value
            SignUpScreen(
                context = context,
                networkState = networkState,
                viewModel = viewModel,
                onSignInClicked = onSignInClicked,
//                currentUser = currentUser
            )
        }
    }
}