package com.closet.xavier.ui.presentation.profile.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.R
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.home.states.UserProfileState
import java.util.Locale

@Composable
fun UserAccountSection(userProfileState: UserProfileState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "User Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))
        when (userProfileState) {
            is UserProfileState.Error -> {}
            is UserProfileState.Loading -> {}
            is UserProfileState.Success -> {
                val profile = userProfileState.profile
                if (profile != null) {
                    val username = profile.firstName.lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } + " " + profile.lastName.lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                    BaseText(
                        text = username,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BaseText(
                        text = profile.email,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(0.7f)
                    )
                }
            }
        }
//        BaseText(
//            text = "UserName",
//            style = MaterialTheme.typography.titleMedium,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        BaseText(
//            text = "UserName",
//            style = MaterialTheme.typography.titleSmall,
//            color = MaterialTheme.colorScheme.onPrimary.copy(0.7f)
//        )

    }

}

@Preview
@Composable
fun UserAccountSectionPreview() {
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
    UserAccountSection(userProfileState)
}