package com.closet.xavier.ui.presentation.products.product_details.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.cart.CartIconBox

@Composable
fun ProductDetailTopSection(
    product: Product? = null,
    modifier: Modifier = Modifier,
    navigateBack: () -> Boolean,
    onFavClicked: (Product) -> Unit,
    onCartNavigation: () -> Unit,
    currentUserId: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        IconButton(
            onClick = { navigateBack() },
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),

                ) {
                Box(
                    modifier = modifier
                        .size(56.dp)
                        .padding(all = 1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }


        }
        Row {
            if (product != null) {
                val isFavourite = remember {
                    mutableStateOf(false)
                }

                val list = product.favorites

                if (list != null) {
                    isFavourite.value = list.any { it.contains(currentUserId, ignoreCase = true) }
                }
                val favIcon = if (isFavourite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                val iconTint =
                    if (isFavourite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                        0.7f
                    )
                IconButton(
                    onClick = { onFavClicked(product) },
                ) {
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),

                        ) {
                        Box(
                            modifier = modifier
                                .size(56.dp)
                                .padding(all = 1.dp)
                        ) {
                            Icon(
                                imageVector = favIcon,
                                contentDescription = "Add to favourite Icon",
                                tint = iconTint,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
            }
            IconButton(
                onClick = { onCartNavigation() },
            ) {

                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),

                    ) {
                    CartIconBox(onCartClicked = { /*TODO*/ })
                }
            }


        }

    }

}