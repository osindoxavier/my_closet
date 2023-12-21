package com.closet.xavier.ui.presentation.profile.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.main.state.AppThemeState

@Composable
fun ProfileInteractionSection(
    modifier: Modifier = Modifier,
    themeState: AppThemeState,
    toggleTheme: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileClickableElement(
            imageVector = Icons.Filled.Person3,
            onActionClicked = { /*TODO*/ },
            actionName = "My Profile"
        )
        ProfileClickableElement(
            imageVector = Icons.Default.ShoppingCart,
            onActionClicked = { /*TODO*/ },
            actionName = "My Cart"
        )
        ProfileClickableElement(
            imageVector = Icons.Default.History,
            onActionClicked = { /*TODO*/ },
            actionName = "My Orders"
        )
        ThemeSwitchSection(
            themeState = themeState,
            toggleTheme = toggleTheme
        )
        ProfileClickableElement(
            imageVector = Icons.Default.Logout,
            onActionClicked = { /*TODO*/ },
            actionName = "Logout"
        )

    }

}

@Composable
fun ProfileClickableElement(
    imageVector: ImageVector, onActionClicked: () -> Unit, actionName: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onActionClicked()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                BaseText(
                    text = actionName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open action icon",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun ThemeSwitchSection(
    themeState: AppThemeState,
    toggleTheme: () -> Unit
) {

    var themeText by remember {
        mutableStateOf("")
    }
    var isDark by remember {
        mutableStateOf(false)
    }

    isDark = when (themeState) {
        AppThemeState.DarkMode -> {
            true
        }

        AppThemeState.LightMode -> {
            false
        }

        AppThemeState.ModeAuto -> {
            isSystemInDarkTheme()
        }
    }

    themeText = if (isDark) "Light Mode" else "Dark Mode"
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            BaseText(
                text = themeText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )
            SwitchThemeIcon(
                themeState = themeState,
                toggleTheme = toggleTheme
            )
        }
    }
}


@Composable
fun SwitchThemeIcon(
    themeState: AppThemeState,
    toggleTheme: () -> Unit
) {

    var isDark by remember {
        mutableStateOf(false)
    }

    isDark = when (themeState) {
        AppThemeState.DarkMode -> {
            true
        }

        AppThemeState.LightMode -> {
            false
        }

        AppThemeState.ModeAuto -> {
            isSystemInDarkTheme()
        }
    }


    Switch(
        checked = isDark,
        onCheckedChange = {
            isDark = it
            toggleTheme()
        }, thumbContent = {
            if (isDark) Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = "Dark Mode",
                modifier = Modifier.size(SwitchDefaults.IconSize),
                tint = MaterialTheme.colorScheme.primary
            ) else Icon(
                imageVector = Icons.Filled.LightMode,
                contentDescription = "Light Mode",
                modifier = Modifier.size(SwitchDefaults.IconSize),
                tint = MaterialTheme.colorScheme.primary
            )
        })
}

@Preview
@Composable
fun ProfileClickableElementPreview() {
    ProfileClickableElement(
        imageVector = Icons.Default.Person, onActionClicked = { /*TODO*/ }, actionName = "Profile"
    )

}


@Preview
@Composable
fun ThemeSwitchSectionPreview() {
    val themeState = AppThemeState.ModeAuto
    val toggleTheme = {}
    ThemeSwitchSection(themeState = themeState, toggleTheme)

}