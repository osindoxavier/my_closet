package com.closet.xavier.ui.presentation.authentication.onboarding.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.R
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun OnBoardingArtSection() {
    val verticalSpace = 55.dp
    val horizontalSpace = 16.dp

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
    }
}

@Preview
@Composable
fun OnBoardingArtSectionPreview() {
    OnBoardingArtSection()
}