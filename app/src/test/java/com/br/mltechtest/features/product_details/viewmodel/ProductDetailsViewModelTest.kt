package com.br.mltechtest.features.product_details.viewmodel

import com.br.mltechtest.core.product_core.domain.exceptions.Exceptions
import com.br.mltechtest.core.product_core.domain.model.ProductDetails
import com.br.mltechtest.core.product_core.domain.usecase.GetProductDetailsUseCase
import com.br.mltechtest.features.product_details.presentation.viewmodel.ProductDetailsViewModel
import com.br.mltechtest.features.product_details.presentation.viewmodel.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    private val getProductDetailsUseCase: GetProductDetailsUseCase = mockk()
    private lateinit var viewModel: ProductDetailsViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = ProductDetailsViewModel(getProductDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given success When getProductDetailsUseCase called Then update uiState`() = runTest {
        // Given
        val expected = UiState(
            screenState = UiState.ScreenState.Loaded,
            product = UiState.Product(
                name = "Produto Teste",
                imagesUrl = listOf("img1.jpg", "img2.jpg"),
            )
        )
        val productId = "abc123"
        val mockProduct = ProductDetails("Produto Teste", listOf("img1.jpg", "img2.jpg"))
        coEvery { getProductDetailsUseCase(productId) } returns Result.success(mockProduct)

        // When
        viewModel.initialize(productId)

        // Then
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `given network failure when getProductDetailsUseCase called then update uiState to ConnectionError`() =
        runTest {
            // Given
            val productId = "abc123"
            coEvery { getProductDetailsUseCase(productId) } returns Result.failure(Exceptions.Network)

            // When
            viewModel.initialize(productId)

            // Then
            assertEquals(UiState.ScreenState.ConnectionError, viewModel.uiState.value.screenState)
        }

    @Test
    fun `given generic failure when getProductDetailsUseCase called then update uiState to ServerError`() =
        runTest {
            // Given
            val productId = "abc123"
            coEvery { getProductDetailsUseCase(productId) } returns Result.failure(RuntimeException())

            // When
            viewModel.initialize(productId)

            // Then
            assertEquals(UiState.ScreenState.ServerError, viewModel.uiState.value.screenState)
        }

    @Test
    fun `given initial state when initialize called twice then useCase is called only once`() =
        runTest {
            // Given
            val productId = "abc123"
            val mockProduct = ProductDetails("Produto Teste", listOf("img1.jpg"))
            coEvery { getProductDetailsUseCase(productId) } returns Result.success(mockProduct)

            // When
            viewModel.initialize(productId)
            viewModel.initialize(productId)

            // Then
            coVerify(exactly = 1) { getProductDetailsUseCase(productId) }
            assertEquals(UiState.ScreenState.Loaded, viewModel.uiState.value.screenState)
        }
}