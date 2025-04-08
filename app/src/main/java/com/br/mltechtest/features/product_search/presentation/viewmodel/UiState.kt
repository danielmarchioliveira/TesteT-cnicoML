package com.br.mltechtest.features.product_search.presentation.viewmodel

data class UiState(
    val query: String = "",
    val screenState: ScreenState = ScreenState.Loading,
    val products: List<Product> = emptyList()
) {
    sealed interface ScreenState {
        data object Loading : ScreenState
        data object Loaded : ScreenState
        data object ConnectionError : ScreenState
        data object ServerError : ScreenState
    }

    data class Product(
        val id: String,
        val name: String,
        val imageUrl: String,
    )
}