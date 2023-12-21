package com.closet.xavier.ui.presentation.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.ui.components.cart.CartIconBox
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.utils.getDaySalutation

@Composable
fun HomeSalutationSection(username: String, modifier: Modifier = Modifier, itemCount: Int = 0) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            BaseText(
                text = "${getDaySalutation()}, $username \uD83D\uDE0A",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(3.dp))
            BaseText(
                text = "Redefine your shoe game",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary.copy(.5f)
            )
        }
        CartIconBox(onCartClicked = {}, itemCount = itemCount)
    }

}

@Preview
@Composable
fun HomeSalutationSectionPreview() {
    HomeSalutationSection(username = "Test", itemCount = 5)

}


