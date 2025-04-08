package com.br.mltechtest.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.br.mltechtest.features.home.presentation.screen.HomeScreen
import com.br.mltechtest.features.product_details.presentation.screen.ProductDetailsScreen
import com.br.mltechtest.features.product_search.presentation.ProductSearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = { query ->
                    navController.navigate(Screen.ProductSearch.createRoute(query))
                }
            )
        }

        composable(
            route = Screen.ProductSearch.route,
            arguments = listOf(navArgument(Screen.ProductSearch.QUERY) {
                type = NavType.StringType
            }),
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString(Screen.ProductSearch.QUERY).orEmpty()

            ProductSearchScreen(
                query = query,
                onNavigateToBack = { navController.popBackStack() },
                onNavigateToSearch = { productId ->
                    navController.navigate(Screen.ProductDetails.createRoute(productId))
                }
            )
        }

        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument(Screen.ProductDetails.PRODUCT_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val productId =
                backStackEntry.arguments?.getString(Screen.ProductDetails.PRODUCT_ID).orEmpty()
            ProductDetailsScreen(
                productId = productId,
                onNavigateToBack = { navController.popBackStack() },
            )
        }
    }
}