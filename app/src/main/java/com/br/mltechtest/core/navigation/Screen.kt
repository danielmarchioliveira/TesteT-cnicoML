package com.br.mltechtest.core.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object ProductSearch : Screen("product_search/{query}") {
        const val QUERY = "query"
        fun createRoute(query: String) = "product_search/$query"
    }

    data object ProductDetails : Screen("product_details/{productId}") {
        const val PRODUCT_ID = "productId"
        fun createRoute(productId: String) = "product_details/$productId"
    }
}