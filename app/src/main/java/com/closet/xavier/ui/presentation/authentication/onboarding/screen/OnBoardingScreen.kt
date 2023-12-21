package com.closet.xavier.ui.presentation.authentication.onboarding.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.closet.xavier.ui.components.dialog.LoadingDialog
import com.closet.xavier.ui.presentation.authentication.onboarding.sections.OnBoardingArtSection
import com.closet.xavier.ui.presentation.authentication.onboarding.sections.OnBoardingButtonSection
import com.closet.xavier.ui.presentation.authentication.onboarding.states.CheckUserProfileState
import com.closet.xavier.ui.presentation.authentication.onboarding.states.CurrentUserState

@Composable
fun OnBoardingScreen(
    newUserState: State<Boolean>,
    loggedInState: State<Boolean>,
    userProfileState: CheckUserProfileState,
    currentUserState: CurrentUserState,
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    val TAG = "OnBoardingScreen"
    val isLoggedIn = loggedInState.value

    Box(modifier = Modifier.fillMaxSize()) {
        OnBoardingArtSection()
        if (!isLoggedIn) {
            //check if user is logged out
            Log.d(TAG, "OnBoardingScreen: isLoggedIn::$isLoggedIn")
            if (newUserState.value) {
                //check if new user
                Log.d(TAG, "OnBoardingScreen: isNewUser::${newUserState.value}")
                OnBoardingButtonSection(
                    onSignUpClicked = navigateToSignUpScreen,
                    onSignInClicked = navigateToSignInScreen,
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            } else {
                Log.d(TAG, "OnBoardingScreen: navigate to sign in")
            }
        } else {
            when (currentUserState) {
                is CurrentUserState.Error -> {
                    val errorMessage = currentUserState.errorMessage
                    Log.e(TAG, "OnBoardingScreen: $errorMessage")
                }

                CurrentUserState.Loading -> TODO()
                is CurrentUserState.Success -> {
                    val currentUser = currentUserState.currentUser
                    if (currentUser != null) {
                        when (userProfileState) {
                            is CheckUserProfileState.Error -> {
                                val errorMessage = userProfileState.errorMessage
                                Log.e(
                                    TAG,
                                    "OnBoardingScreen: errorUserProfile::$$errorMessage",
                                )
                            }

                            CheckUserProfileState.Loading -> {
                                LoadingDialog()
                            }

                            is CheckUserProfileState.Success -> {
                                val userProfile = userProfileState.isProfilePresent
                                Log.d(TAG, "OnBoardingScreen: userProfile::$userProfile")
                                if (userProfile) {
                                    navigateToHomeScreen()
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun OnBoardingScreenLoadingPreview() {
    val currentUserState = CurrentUserState.Loading
    val userProfileState = CheckUserProfileState.Loading
    val loggedInState = remember {
        mutableStateOf(false)
    }
    val newUserState = remember {
        mutableStateOf(false)
    }

    val navigateToHomeScreen = {}
    val navigateToSignInScreen = {}
    val navigateToSignUpScreen = {}

    OnBoardingScreen(
        newUserState = newUserState,
        loggedInState = loggedInState,
        userProfileState = userProfileState,
        currentUserState = currentUserState,
        navigateToHomeScreen = navigateToHomeScreen,
        navigateToSignInScreen = navigateToSignInScreen,
        navigateToSignUpScreen = navigateToSignUpScreen
    )

}

@Preview
@Composable
fun OnBoardingScreenSuccessPreview() {
    val currentUserState = CurrentUserState.Success(currentUser = null)
    val userProfileState = CheckUserProfileState.Success()
    val loggedInState = remember {
        mutableStateOf(false)
    }
    val newUserState = remember {
        mutableStateOf(true)
    }

    val navigateToHomeScreen = {}

    val navigateToSignInScreen = {}
    val navigateToSignUpScreen = {}

    OnBoardingScreen(
        newUserState = newUserState,
        loggedInState = loggedInState,
        userProfileState = userProfileState,
        currentUserState = currentUserState,
        navigateToHomeScreen = navigateToHomeScreen,
        navigateToSignInScreen = navigateToSignInScreen,
        navigateToSignUpScreen = navigateToSignUpScreen
    )
}



