package com.closet.xavier.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun BaseOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    textColor: Color = Color.White,
    text: String,
    shapePercent: Int = 0,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        border = BorderStroke(1.dp, borderColor),
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