package com.closet.xavier.ui.presentation.authentication.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.R
import com.closet.xavier.ui.components.button.BaseButton
import com.closet.xavier.ui.components.button.BaseOutlineButton
import com.closet.xavier.ui.components.dialog.LoadingDialog
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.authentication.view_model.AuthStateViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    onSignUpNavigation: () -> Unit = {},
    onSignInNavigation: () -> Unit = {},
    state: State<Boolean>,
    saveNewUserStatus: () -> Unit,
    currentUser: FirebaseUser?,
    authStateViewModel: AuthStateViewModel,
    isLoading: MutableState<Boolean>
) {
    val TAG = "OnBoardingScreen"
    val verticalSpace = 55.dp
    val horizontalSpace = 16.dp
    val isFirstTimeUser = state.value
    val scope = rememberCoroutineScope()


    if (isLoading.value) {
        LoadingDialog()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.shoes_art),
            contentDescription = "Shoes Pic art",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(0.5f), blendMode = BlendMode.Darken)
        )

        BaseText(
            text = "Redefine your\nShoe game",
            style = MaterialTheme.typography.headlineMedium,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = horizontalSpace)
        )


        if (currentUser == null) {
            Log.d(TAG, "OnBoardingScreen: user is logged out")
            if (!isFirstTimeUser) {
                onSignInNavigation()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = horizontalSpace, vertical = verticalSpace)

                ) {
                    BaseButton(
                        onClick = {
                            scope.launch {
                                saveNewUserStatus()
                                onSignUpNavigation()
                            }
                        },
                        shapePercent = 12,
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sign Up",
                        fillColor = Color.White,
                        contentColor = Color.Black,
                        textColor = Color.Black
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    BaseOutlineButton(
                        onClick = {
                            scope.launch {
                                saveNewUserStatus()
                                onSignInNavigation()
                            }
                        },
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        shapePercent = 15
                    )
                }
            }

        } else {
            Log.d(TAG, "OnBoardingScreen: ${currentUser.uid}")
            authStateViewModel.checkUserProfile(userId = currentUser.uid)
        }
    }
}