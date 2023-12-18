package com.closet.xavier.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.navigation.models.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Store,
        BottomNavItem.Favourite,
        BottomNavItem.Profile
    )
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    NavigationBar {


        navItems.forEachIndexed { index, navigationItem ->
            val color =
                if (index == navigationSelectedItem) MaterialTheme.colorScheme.primary else Color.Black

            NavigationBarItem(
                selected = index == navigationSelectedItem,
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {

                    Icon(
                        painter = painterResource(id = navigationItem.icon),
                        contentDescription = navigationItem.name,
                        tint = color
                    )
                },
                label = {
                    BaseText(text = navigationItem.name, color = color)
                }
            )

        }

    }


}