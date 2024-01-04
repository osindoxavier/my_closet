package com.closet.xavier.ui.navigation.models

sealed class AuthenticationNavItem(val route:String, val name:String){
    data object OnboardingScreen: AuthenticationNavItem(route = "nav_on_board", name = "OnboardScreen")
    data object SignUpScreen: AuthenticationNavItem(route = "nav_sign_up", name = "SignUpScreen")
    data object SignInScreen: AuthenticationNavItem(route = "nav_sign_in", name = "SignInScreen")
}
