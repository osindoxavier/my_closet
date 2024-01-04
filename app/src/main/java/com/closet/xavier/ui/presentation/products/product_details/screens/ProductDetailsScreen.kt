package com.closet.xavier.ui.presentation.products.product_details.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.dialog.LoadingDialog
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.presentation.products.product_details.sections.ProductDetailTopSection
import com.closet.xavier.ui.presentation.products.product_details.sections.ProductDetailsSection
import com.closet.xavier.ui.presentation.products.product_details.sections.ProductPhotoSection
import com.closet.xavier.ui.presentation.products.product_details.states.ProductState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navigateBack: () -> Boolean,
    onFavClicked: (Product) -> Unit,
    productState: ProductState,
    currentUserState: String,
) {
    BaseScaffold(
        content = {
            ProductDetailsScreenContent(
                modifier = Modifier.padding(it),
                navigateBack = navigateBack,
                productState = productState,
                currentUserState = currentUserState,
                onFavClicked = onFavClicked
            )
        },
    )


}

@Composable
fun ProductDetailsScreenContent(
    modifier: Modifier = Modifier,
    navigateBack: () -> Boolean,
    productState: ProductState,
    onFavClicked: (Product) -> Unit,
    currentUserState: String,
) {

    val context = LocalContext.current
    var loadingDialog by remember {
        mutableStateOf(false)
    }

    if (loadingDialog) {
        LoadingDialog()
    }
    when (productState) {
        is ProductState.Error -> {
            val error = productState.errorMessage
            loadingDialog = false
            Box(modifier = Modifier.fillMaxSize()) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                ProductDetailTopSection(
                    navigateBack = navigateBack,
                    onFavClicked = onFavClicked,
                    onCartNavigation = { /*TODO*/ },
                    currentUserId = currentUserState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        ProductState.Loading -> {
            loadingDialog = true
        }

        is ProductState.Success -> {
            val product = productState.product
            val images = productState.images
            loadingDialog = false
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxWidth()
                ) {
                    ProductPhotoSection(
                        product = product,
                        navigateBack = navigateBack,
                        images = images,
                        onFavClicked = onFavClicked,
                        currentUserId = currentUserState

                    )
                }
                Box(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                ) {
                    ProductDetailsSection(
                        product = product
                    )
                }
            }
        }
    }
}







