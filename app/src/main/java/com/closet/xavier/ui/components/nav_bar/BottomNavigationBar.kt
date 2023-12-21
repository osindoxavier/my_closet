package com.closet.xavier.ui.components.nav_bar

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.closet.xavier.ui.navigation.models.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Store,
        BottomNavItem.Favourite,
        BottomNavItem.Profile
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
//    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.surface,
//        contentColor = MaterialTheme.colorScheme.surface
//    ) {
//
//
//        navItems.forEachIndexed { index, navigationItem ->
//            NavigationBarItem(
//                selected = selectedItemIndex == index,
//                onClick = {
//                    selectedItemIndex = index
//                    navController.navigate(navigationItem.route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//
//                },
//                icon = {
//
//                    Icon(
//                        painter = painterResource(id = navigationItem.icon),
//                        contentDescription = navigationItem.name
//                    )
//                },
//                label = {
//                    BaseText(text = navigationItem.name)
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    indicatorColor = MaterialTheme.colorScheme.surface,
//                    selectedIconColor = MaterialTheme.colorScheme.primary,
//                    selectedTextColor = MaterialTheme.colorScheme.primary,
//                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
//                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
//                )
//
//            )
//
//        }
//
//    }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.name)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasUpdate) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }


}