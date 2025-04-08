package com.br.mltechtest.features.product_details.presentation.viewmodel

data class UiState(
    val screenState: ScreenState = ScreenState.Loading,
    val product: Product = Product(name = "", imagesUrl = emptyList())
) {
    sealed interface ScreenState {
        data object Loading : ScreenState
        data object Loaded : ScreenState
        data object ConnectionError : ScreenState
        data object ServerError : ScreenState
    }

    data class Product(
        val name: String,
        val imagesUrl: List<String>,
    )
}