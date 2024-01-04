package com.closet.xavier.ui.presentation.home.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.closet.xavier.R.*
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.components.nav_bar.BottomNavigationBar
import com.closet.xavier.ui.components.dialog.LoadingDialog
import com.closet.xavier.ui.components.scaffold.BaseScaffold
import com.closet.xavier.ui.presentation.home.sections.BannerSection
import com.closet.xavier.ui.presentation.home.sections.CategorySection
import com.closet.xavier.ui.presentation.home.sections.HomeSalutationSection
import com.closet.xavier.ui.presentation.home.sections.PopularShoesSection
import com.closet.xavier.ui.presentation.home.sections.RecentProductSection
import com.closet.xavier.ui.presentation.home.states.BrandsState
import com.closet.xavier.ui.presentation.home.states.ProductsState
import com.closet.xavier.ui.presentation.home.states.UserProfileState
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userProfileState: UserProfileState,
    brandsState: BrandsState,
    popularProductState: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit,
) {
    BaseScaffold(content = {
        HomeScreenContent(
            modifier = Modifier.padding(it),
            userProfile = userProfileState,
            brands = brandsState,
            popularProducts = popularProductState,
            onFavClicked = onFavClicked,
            onProductClicked = onProductClicked
        )
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    })
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    userProfile: UserProfileState,
    brands: BrandsState,
    popularProducts: ProductsState,
    onFavClicked: (Product) -> Unit,
    onProductClicked: (Product) -> Unit
) {
    val verticalSpace = 18.dp
    val horizontalSpace = 16.dp


    val currentUserId = remember {
        mutableStateOf("")
    }

    val isLoadingDialog = remember {
        mutableStateOf(false)
    }

    if (isLoadingDialog.value) {
        LoadingDialog()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = verticalSpace, horizontal = horizontalSpace)
            .verticalScroll(rememberScrollState())
    ) {
        when (userProfile) {
            is UserProfileState.Error -> {
                isLoadingDialog.value = false
            }

            UserProfileState.Loading -> {
                isLoadingDialog.value = true
            }

            is UserProfileState.Success -> {
                currentUserId.value = userProfile.profile?.uid ?: ""
                val username = userProfile.profile?.firstName?.lowercase()
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                HomeSalutationSection(username = username ?: "Client", itemCount = 1)
                isLoadingDialog.value = false
            }

            is UserProfileState.Error -> TODO()
            UserProfileState.Loading -> TODO()
            is UserProfileState.Success -> TODO()
            else -> {}
        }

        when (brands) {
            is BrandsState.Error -> {
                isLoadingDialog.value = false
            }

            is BrandsState.Loading -> {
                isLoadingDialog.value = true
            }

            is BrandsState.Success -> {
                val brands = brands.brandList
                CategorySection(brands = brands)
                isLoadingDialog.value = false
            }

            else -> {}
        }
        BannerSection()
        when (popularProducts) {
            is ProductsState.Error -> {
                isLoadingDialog.value = false
            }

            is ProductsState.Loading -> {
                isLoadingDialog.value = true
            }

            is ProductsState.Success -> {
                if (popularProducts.products != null) {
                    PopularShoesSection(
                        popularProducts = popularProducts.products,
                        onFavClicked = onFavClicked,
                        onProductClicked = onProductClicked,
                        userId = currentUserId.value
                    )
                    isLoadingDialog.value = false
                }
            }

            else -> {}
        }
        RecentProductSection()
    }
}












