package com.closet.xavier.ui.presentation.products.store.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.closet.xavier.ui.components.text.BaseSearchBox
import com.closet.xavier.ui.components.text.BaseText
import com.closet.xavier.ui.presentation.home.sections.CategorySection
import com.closet.xavier.ui.presentation.home.states.BrandsState
import com.closet.xavier.ui.presentation.home.states.ProductsState
import com.closet.xavier.ui.presentation.products.store.section.ProductsGridSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    navController: NavController,
    userIdState: String,
    brandsState: BrandsState,
    productsState: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {
    BaseScaffold(
        topBar = {
            TopAppBar(
                title = {
                    BaseText(
                        text = "Store",
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
            StoreScreenContent(
                modifier = Modifier.padding(it),
                userId = userIdState,
                brandsState = brandsState,
                productsState = productsState,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked
            )
        }, bottomBar = {
            BottomNavigationBar(navController = navController)
        })

}

@Composable
fun StoreScreenContent(
    modifier: Modifier = Modifier,
    userId: String,
    brandsState: BrandsState,
    productsState: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {
    val verticalSpace = 8.dp
    val horizontalSpace = 8.dp


    val isLoadingDialog = remember {
        mutableStateOf(false)
    }

    if (isLoadingDialog.value) {
        LoadingDialog()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = verticalSpace, horizontal = horizontalSpace)
    ) {

        BaseSearchBox()

        when (brandsState) {
            is BrandsState.Error -> {
                isLoadingDialog.value = false
            }

            is BrandsState.Loading -> {
                isLoadingDialog.value = true
            }

            is BrandsState.Success -> {
                val brands = brandsState.brandList
                CategorySection(brands = brands)
                isLoadingDialog.value = false
            }
        }

        when (productsState) {
            is ProductsState.Error -> {
                isLoadingDialog.value = false
            }

            is ProductsState.Loading -> {
                isLoadingDialog.value = true
            }

            is ProductsState.Success -> {
                if (productsState.products != null) {
                    ProductsGridSection(
                        products = productsState.products,
                        onFavClicked = onFavClicked,
                        onProductClicked = onProductClicked,
                        userId = userId
                    )
                    isLoadingDialog.value = false
                }
            }

        }
    }
}
