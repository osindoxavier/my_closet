package com.closet.xavier.ui.presentation.authentication.onboarding.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.button.BaseButton
import com.closet.xavier.ui.components.button.BaseOutlineButton

@Composable
fun OnBoardingButtonSection(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit,
    onSignInClicked: () -> Unit
) {
    val verticalSpace = 55.dp
    val horizontalSpace = 16.dp
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalSpace, vertical = verticalSpace)

    ) {
        BaseButton(
            onClick = onSignUpClicked,
            shapePercent = 12,
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Up",
            fillColor = Color.White,
            contentColor = Color.Black,
            textColor = Color.Black
        )
        Spacer(modifier = Modifier.height(15.dp))
        BaseOutlineButton(
            onClick = onSignInClicked,
            text = "Sign In",
            modifier = Modifier.fillMaxWidth(),
            shapePercent = 15
        )
    }
}

@Preview
@Composable
fun OnBoardingButtonSectionPreview() {
    OnBoardingButtonSection(
        onSignUpClicked = {},
        onSignInClicked = {})

}