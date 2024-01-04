package com.closet.xavier.ui.components.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shapePercent: Int = 0,
    text: String,
    textColor: Color= Color.White
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = fillColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(
            topEndPercent = shapePercent,
            topStartPercent = shapePercent,
            bottomEndPercent = shapePercent,
            bottomStartPercent = shapePercent
        )
    ) {
        BaseText(
            text = text,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }

}