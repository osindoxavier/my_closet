package com.closet.xavier.ui.presentation.products.product_details.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun ProductNameAndPriceSection(product: Product, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseText(
            text = product.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
        BaseText(
            text = "Ksh ${product.price}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

    }
    Divider()


}