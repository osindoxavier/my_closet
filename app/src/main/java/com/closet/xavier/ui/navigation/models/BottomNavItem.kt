package com.closet.xavier.ui.navigation.models

import androidx.annotation.DrawableRes
import com.closet.xavier.R

sealed class BottomNavItem(
    val route: String,
    val name: String,
    @DrawableRes val icon: Int = 0,
    val hasUpdate: Boolean = false
) {
    data object Home : BottomNavItem(route = "nav_home", name = "Home", icon = R.drawable.ic_home)
    data object Store :
        BottomNavItem(
            route = "nav_store",
            name = "Store",
            icon = R.drawable.ic_closet,
            hasUpdate = false
        )

    data object ProductDetails :
        BottomNavItem(route = "nav_product_details", name = "Product Details", hasUpdate = false)

    data object Favourite :
        BottomNavItem(
            route = "nav_favourite",
            name = "Favourite",
            icon = R.drawable.ic_favorite,
            hasUpdate = false
        )

    data object Profile :
        BottomNavItem(
            route = "nav_profile",
            name = "Profile",
            icon = R.drawable.ic_person,
            hasUpdate = false
        )
}
