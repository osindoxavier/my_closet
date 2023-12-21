package com.closet.xavier.ui.presentation.favourite.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.nav_bar.BottomNavigationBar
import com.closet.xavier.ui.components.cart.CartIconBox
import com.closet.xavier.ui.components.dialog.LoadingDialog
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.home.states.ProductsState
import com.closet.xavier.ui.presentation.products.store.section.ProductsGridSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navController: NavController,
    userIdState: String,
    productsState: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {

    BaseScaffold(
        topBar = {
            TopAppBar(
                title = {
                    BaseText(
                        text = "My Favourites",
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        letterSpacing = 1.5.sp
                    )
                },
                actions = {
                    CartIconBox(onCartClicked = {}, itemCount = 10)
                }
            )
        },
        content = {
            FavoriteScreenContent(
                modifier = Modifier.padding(it),
                userId = userIdState,
                productsState = productsState,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked
            )
        }, bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    )
}

@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier,
    userId: String,
    productsState: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {
    val isLoadingDialog = remember {
        mutableStateOf(false)
    }

    if (isLoadingDialog.value) {
        LoadingDialog()
    }

    val verticalSpace = 8.dp
    val horizontalSpace = 8.dp

    when (productsState) {
        is ProductsState.Error -> {
            isLoadingDialog.value = false
        }

        is ProductsState.Loading -> {
            isLoadingDialog.value = true
        }

        is ProductsState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = verticalSpace, horizontal = horizontalSpace)
            ) {
                if (!productsState.products.isNullOrEmpty()) {
                    ProductsGridSection(
                        products = productsState.products,
                        onFavClicked = onFavClicked,
                        onProductClicked = onProductClicked,
                        userId = userId
                    )
                    isLoadingDialog.value = false
                } else {
                    BaseText(
                        text = "No Favorites!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Center)

                    )
                    isLoadingDialog.value = false
                }

            }

        }

    }
}
