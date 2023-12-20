package com.closet.xavier.ui.navigation.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.ui.navigation.models.BottomNavItem
import com.closet.xavier.ui.navigation.models.RootNavItem
import com.closet.xavier.ui.presentation.home.screens.HomeScreen
import com.closet.xavier.ui.presentation.home.view_model.HomeViewModel
import com.closet.xavier.ui.presentation.store.product_details.screens.ProductDetailsScreen
import com.closet.xavier.ui.presentation.store.product_details.view_model.ProductDetailsViewModel


fun NavGraphBuilder.bottomNavGraph(navController: NavController) {
    navigation(
        startDestination = BottomNavItem.Home.name,
        route = RootNavItem.BottomNavigation.id.toString()
    ) {
        composable(route = BottomNavItem.Home.route) {
//            val viewModel: HomeViewModel = hiltViewModel()
//            val userProfileState = viewModel.userProfileState.collectAsStateWithLifecycle()
//            val brandsState = viewModel.brandsState.collectAsStateWithLifecycle()
//            val popularProductState = viewModel.popularProductsState.collectAsStateWithLifecycle()
//
//            val onFavClicked = { product: Product ->
//                viewModel.addFavoriteProduct(productId = product.productId)
//            }
//
//            val onProductClicked = { product: Product ->
//                navController.navigate(BottomNavItem.ProductDetails.route + "/${product.productId}")
//            }

            HomeScreen(
                navController = navController,
//                userProfileState = userProfileState,
//                brandsState = brandsState,
//                popularProductState = popularProductState,
//                onFavClicked = onFavClicked,
//                onProductClicked = onProductClicked,
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

        }
        composable(route = BottomNavItem.Favourite.route) {

        }
        composable(route = BottomNavItem.Profile.route) {}

    }
}