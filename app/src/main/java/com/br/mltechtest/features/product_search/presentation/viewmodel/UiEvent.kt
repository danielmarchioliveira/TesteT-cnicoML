package com.br.mltechtest.features.product_search.presentation.viewmodel

sealed class UiEvent {
    data class NavigateToProductDetails(val productId: String) : UiEvent()
}