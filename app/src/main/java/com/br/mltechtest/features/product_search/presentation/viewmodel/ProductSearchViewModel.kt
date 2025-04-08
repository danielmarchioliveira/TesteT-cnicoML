package com.br.mltechtest.features.product_search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.mltechtest.core.product_core.domain.exceptions.Exceptions
import com.br.mltechtest.core.product_core.domain.model.Product
import com.br.mltechtest.core.product_core.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun initialize(query: String) {
        if (_uiState.value.screenState is UiState.ScreenState.Loaded) return
        fetchProducts(query)
    }

    private fun fetchProducts(query: String) {
        viewModelScope.launch {
            val result = searchProductsUseCase(query)

            result.onSuccess { products ->
                _uiState.update { product ->
                    product.copy(
                        products = products.map { it.toUiModel() },
                        screenState = UiState.ScreenState.Loaded
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        screenState = if (exception is Exceptions.Network) {
                            UiState.ScreenState.ConnectionError
                        } else {
                            UiState.ScreenState.ServerError
                        }
                    )
                }
            }
        }
    }

    private fun Product.toUiModel() = UiState.Product(
        id = id,
        name = name,
        imageUrl = imagesUrl.firstOrNull().orEmpty()
    )

    fun onItemClick(productId: String) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateToProductDetails(productId))
        }
    }
}