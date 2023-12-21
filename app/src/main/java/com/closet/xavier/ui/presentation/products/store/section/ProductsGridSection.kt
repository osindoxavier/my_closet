package com.closet.xavier.ui.presentation.products.store.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.presentation.home.sections.ProductItemElement

@Composable
fun ProductsGridSection(
    products: List<Product>,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
    userId: String
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(products) { item ->
                ProductItemElement(
                    product = item,
                    onFavClicked = onFavClicked,
                    onProductClicked = onProductClicked,
                    userId = userId
                )
            }
        },
        contentPadding = PaddingValues(all = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight()
    )
}

@Preview
@Composable
fun StoreProductSectionPreview() {
    val productList = mutableListOf<Product>()
    (1..50).forEach { index ->
        val product = Product(
            brandId = index.toString(),
            favorites = null,
            productId = index.toString(),
            image = index.toString(),
            name = index.toString(),
            popular = false,
            price = index.toString(),
            thumb = null
        )
        productList.add(product)

    }
    val productInteraction = { product: Product ->

    }
    ProductsGridSection(
        products = productList,
        onFavClicked = productInteraction,
        onProductClicked = productInteraction,
        userId = ""
    )

}


