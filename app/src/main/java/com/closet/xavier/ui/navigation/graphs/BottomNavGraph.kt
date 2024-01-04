package com.closet.xavier.ui.navigation.graphs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.navigation.models.BottomNavItem
import com.closet.xavier.ui.navigation.models.RootNavItem
import com.closet.xavier.ui.presentation.favourite.screens.FavouriteScreen
import com.closet.xavier.ui.presentation.favourite.view_model.FavouriteViewModel
import com.closet.xavier.ui.presentation.home.screens.HomeScreen
import com.closet.xavier.ui.presentation.home.view_model.HomeViewModel
import com.closet.xavier.ui.presentation.main.state.AppThemeState
import com.closet.xavier.ui.presentation.profile.screens.ProfileScreen
import com.closet.xavier.ui.presentation.profile.view_model.ProfileViewModel
import com.closet.xavier.ui.presentation.products.product_details.screens.ProductDetailsScreen
import com.closet.xavier.ui.presentation.products.product_details.view_model.ProductDetailsViewModel
import com.closet.xavier.ui.presentation.products.store.screens.StoreScreen
import com.closet.xavier.ui.presentation.products.store.view_models.StoreViewModel


fun NavGraphBuilder.bottomNavGraph(
    navController: NavController,
    onThemeUpdated: () -> Unit,
    themeState: AppThemeState
) {
    navigation(
        startDestination = BottomNavItem.Home.name,
        route = RootNavItem.BottomNavigation.id.toString()
    ) {
        composable(route = BottomNavItem.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val userProfileState = viewModel.userProfileState.collectAsStateWithLifecycle().value
            val brandsState = viewModel.brandsState.collectAsStateWithLifecycle().value
            val popularProductState =
                viewModel.popularProductsState.collectAsStateWithLifecycle().value

            val onFavClicked = { product: Product ->
                viewModel.addFavoriteProduct(productId = product.productId)
            }

            val onProductClicked = { product: Product ->
                navController.navigate(BottomNavItem.ProductDetails.route + "/${product.productId}")
            }

            HomeScreen(
                navController = navController,
                userProfileState = userProfileState,
                brandsState = brandsState,
                popularProductState = popularProductState,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked,
            )
        }

        composable(route = BottomNavItem.ProductDetails.route + "/{productId}") {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            val productState = viewModel.getProductByIdState.collectAsStateWithLifecycle()
            val productImagesState = viewModel.productImagesState.collectAsStateWithLifecycle()
            val navigateBack = {
                navController.popBackStack()
            }
            ProductDetailsScreen(
                productState = productState,
                navigateBack = navigateBack,
                productImagesState = productImagesState
            )
        }
        composable(route = BottomNavItem.Store.route) {
            val viewModel: StoreViewModel = hiltViewModel()
            val userIdState = viewModel.userIdState.collectAsStateWithLifecycle().value
            val brandsState = viewModel.brandsState.collectAsStateWithLifecycle().value
            val productsState = viewModel.productsState.collectAsStateWithLifecycle().value

            val onFavClicked = { product: Product ->
                viewModel.addFavoriteProduct(productId = product.productId)
            }

            val onProductClicked = { product: Product ->
                navController.navigate(BottomNavItem.ProductDetails.route + "/${product.productId}")
            }

            StoreScreen(
                navController = navController,
                userIdState = userIdState,
                brandsState = brandsState,
                productsState = productsState,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked
            )


        }
        composable(route = BottomNavItem.Favourite.route) {
            val viewModel: FavouriteViewModel = hiltViewModel()
            val userIdState = viewModel.userIdState.collectAsStateWithLifecycle().value
            val productsState = viewModel.productsState.collectAsStateWithLifecycle().value

            val onFavClicked = { product: Product ->
                viewModel.addFavoriteProduct(productId = product.productId)
            }

            val onProductClicked = { product: Product ->
                navController.navigate(BottomNavItem.ProductDetails.route + "/${product.productId}")
            }
            FavouriteScreen(
                navController = navController,
                userIdState = userIdState,
                productsState = productsState,
                onFavClicked = onFavClicked,
                onProductClicked = onProductClicked
            )

        }
        composable(route = BottomNavItem.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val userProfileState = viewModel.userProfileState.collectAsStateWithLifecycle().value
            var darkTheme by remember { mutableStateOf(false) }
            when (themeState) {
                is AppThemeState.DarkMode -> {
                    DarkTheme(isDark = true)
                    darkTheme = true
                }

                is AppThemeState.LightMode -> {
                    DarkTheme(isDark = false)
                    darkTheme = false
                }

                is AppThemeState.ModeAuto -> {
                    DarkTheme(isDark = isSystemInDarkTheme())
                    darkTheme = isSystemInDarkTheme()
                }
            }

            ProfileScreen(
                navController = navController, userProfileState = userProfileState,
                themeState = themeState,
                toggleTheme = onThemeUpdated,
            )
        }

    }
}