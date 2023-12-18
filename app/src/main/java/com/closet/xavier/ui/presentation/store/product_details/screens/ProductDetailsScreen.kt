package com.closet.xavier.ui.presentation.store.product_details.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.presentation.store.product_details.sections.ProductDetailsSection
import com.closet.xavier.ui.presentation.store.product_details.sections.ProductPhotoSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productState: State<Product>,
    navigateBack: () -> Boolean,
    productImagesState: State<List<String>>
) {
    BaseScaffold(
        content = {
            ProductDetailsScreenContent(
                product = productState.value,
                modifier = Modifier.padding(it),
                navigateBack = navigateBack,
                images = productImagesState.value
            )
        },
    )


}

@Composable
fun ProductDetailsScreenContent(
    modifier: Modifier = Modifier,
    product: Product,
    navigateBack: () -> Boolean,
    images: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier
            .weight(4f)
            .fillMaxWidth()) {
            ProductPhotoSection(
                navigateBack = navigateBack,
                images = images
            )
        }
        Box(modifier = Modifier
            .weight(6f)
            .fillMaxWidth()) {
            ProductDetailsSection(
                product = product
            )
        }
    }
}







