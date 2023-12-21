package com.closet.xavier.ui.presentation.profile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.ui.components.nav_bar.BottomNavigationBar
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.home.states.UserProfileState
import com.closet.xavier.ui.presentation.main.state.AppThemeState
import com.closet.xavier.ui.presentation.profile.section.ProfileInteractionSection
import com.closet.xavier.ui.presentation.profile.section.UserAccountSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userProfileState: UserProfileState,
    toggleTheme: () -> Unit,
    themeState: AppThemeState
) {
    BaseScaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                BaseText(
                    text = "Profile",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
        )
    }, content = {
        ProfileScreenContent(
            modifier = Modifier.padding(it),
            userProfileState = userProfileState,
            themeState = themeState,
            toggleTheme = toggleTheme
        )
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    })

}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    userProfileState: UserProfileState,
    themeState: AppThemeState,
    toggleTheme: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        UserAccountSection(userProfileState = userProfileState)
        Column(modifier = Modifier.fillMaxHeight(0.7f)) {
            ProfileInteractionSection(
                themeState = themeState,
                toggleTheme = toggleTheme
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenContentPreview() {
    val userProfileState: UserProfileState = UserProfileState.Success(
        profile = UserProfile(
            uid = "test",
            firstName = "test",
            middleName = "test",
            lastName = "test",
            email = "test@gmail.com",
            phone = "07123456789"
        )
    )
    val themeState = AppThemeState.ModeAuto
    val toggleTheme = {}

    ProfileScreenContent(
        userProfileState = userProfileState,
        toggleTheme = toggleTheme,
        themeState = themeState
    )

}




