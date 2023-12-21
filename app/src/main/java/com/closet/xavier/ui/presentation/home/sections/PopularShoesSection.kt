package com.closet.xavier.ui.presentation.home.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.images.ProductImageBox
import com.closet.xavier.ui.components.text.BaseText


@Composable
fun PopularShoesSection(
    modifier: Modifier = Modifier,
    popularProducts: List<Product>,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    userId: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        BaseText(
            text = "Best Seller",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow(
            content = {
                items(popularProducts) { item ->
                    ProductItemElement(
                        product = item,
                        onFavClicked = onFavClicked,
                        onProductClicked = onProductClicked,
                        userId = userId
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
    }
}