package com.closet.xavier.ui.navigation.models

import androidx.annotation.StringRes
import com.closet.xavier.R

sealed class RootNavItem(val route: String, @StringRes val id: Int) {
    data object RootNavigation :
        RootNavItem(route = "root_authentication", id = R.string.nav_root)

    data object Authentication :
        RootNavItem(route = "root_authentication", id = R.string.nav_authentication)

    data object BottomNavigation :
        RootNavItem(route = "root_bottom_navigation", id = R.string.nav_bottom_navigation)

}
