package com.closet.xavier.ui.presentation.home.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.images.ProductImageBox
import com.closet.xavier.ui.components.text.BaseText

@Composable
fun ProductItemElement(
    modifier: Modifier = Modifier,
    product: Product,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    userId: String
) {
    val isFavourite = remember {
        mutableStateOf(false)
    }

    val list = product.favorites

    if (list != null) {
        isFavourite.value = list.any { it.contains(userId, ignoreCase = true) }
    }
    val favIcon = if (isFavourite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val iconTint =
        if (isFavourite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary.copy(
            0.7f
        )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(all = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                ProductImageBox(
                    imageUrl = product.image,
                    size = 202.dp,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.clickable { onProductClicked(product) },
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClicked(product) },
                    horizontalAlignment = Alignment.Start
                ) {
                    BaseText(
                        text = product.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    BaseText(
                        text = "Ksh ${product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(
                onClick = { onFavClicked(product) }, modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = favIcon, contentDescription = "Favourite Icon", tint = iconTint)
            }
        }
    }

}

@Preview
@Composable
fun ProductItemElementPreview() {

    val product = Product(
        brandId = "test",
        favorites = null,
        productId = "test",
        image = "",
        name = "test",
        popular = false,
        price = "12000",
        thumb = null
    )

    val onFavClicked = { product: Product ->

    }

    val onProductClicked = { product: Product ->

    }

    ProductItemElement(
        product = product,
        onFavClicked = onFavClicked,
        onProductClicked = onProductClicked,
        userId = ""
    )

}