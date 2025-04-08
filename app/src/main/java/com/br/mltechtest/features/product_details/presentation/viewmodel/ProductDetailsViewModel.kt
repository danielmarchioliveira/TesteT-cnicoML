package com.br.mltechtest.features.product_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.mltechtest.core.product_core.domain.exceptions.Exceptions
import com.br.mltechtest.core.product_core.domain.model.ProductDetails
import com.br.mltechtest.core.product_core.domain.usecase.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun initialize(productId: String) {
        if (_uiState.value.screenState is UiState.ScreenState.Loaded) return
        fetchProductDetails(productId)
    }

    private fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.ScreenState.Loading)

            val result = getProductDetailsUseCase(productId)

            result.onSuccess { product ->
                _uiState.update {
                    it.copy(
                        product = product.toUiModel(),
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

    private fun ProductDetails.toUiModel() = UiState.Product(
        name = name,
        imagesUrl = imagesUrl.map { it }
    )
}