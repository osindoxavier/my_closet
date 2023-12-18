package com.closet.xavier.ui.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.closet.xavier.R
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun GoogleButton(
    name:String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shapePercent: Int = 0
) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(
            topEndPercent = shapePercent,
            topStartPercent = shapePercent,
            bottomEndPercent = shapePercent,
            bottomStartPercent = shapePercent
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BaseText(
            text = name,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}