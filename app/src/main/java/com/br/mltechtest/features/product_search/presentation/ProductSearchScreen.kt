package com.br.mltechtest.features.product_search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.mltechtest.core.designsystem.components.ProductItem
import com.br.mltechtest.core.designsystem.components.TopBar
import com.br.mltechtest.core.designsystem.theme.MLTechTestTheme
import com.br.mltechtest.features.product_search.presentation.viewmodel.ProductSearchViewModel
import com.br.mltechtest.features.product_search.presentation.viewmodel.UiEvent
import com.br.mltechtest.features.product_search.presentation.viewmodel.UiState

@Composable
fun ProductSearchScreen(
    query: String,
    onNavigateToBack: () -> Unit,
    onNavigateToSearch: (String) -> Unit,
    viewModel: ProductSearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(query)
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToProductDetails -> {
                    onNavigateToSearch(event.productId)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = query,
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        ProductSearchContent(
            uiState = uiState,
            onItemClick = viewModel::onItemClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ProductSearchContent(
    uiState: UiState,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.screenState) {
        UiState.ScreenState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        UiState.ScreenState.ConnectionError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Sem conexão com a internet. Verifique sua rede.")
            }
        }

        UiState.ScreenState.ServerError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Erro no servidor. Tente novamente mais tarde.")
            }
        }

        UiState.ScreenState.Loaded -> {
            val products = uiState.products
            if (products.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum produto encontrado.")
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(products) { product ->
                        ProductItem(
                            imageUrl = product.imageUrl,
                            name = product.name,
                            onClick = { onItemClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSearchScreenLoaded() {
    MLTechTestTheme {
        ProductSearchContent(
            uiState = UiState(
                query = "Celular",
                screenState = UiState.ScreenState.Loaded,
                products = listOf(
                    UiState.Product(
                        id = "1",
                        imageUrl = "",
                        name = "Smartphone Galaxy S Ultra com nome bem grande para testar",
                    ),
                    UiState.Product(
                        id = "2",
                        imageUrl = "",
                        name = "iPhone 14 Pro Max",
                    )
                )
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSearchScreenLoading() {
    MLTechTestTheme {
        ProductSearchContent(
            uiState = UiState(
                query = "Celular",
                screenState = UiState.ScreenState.Loading
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSearchScreenConnectionError() {
    MLTechTestTheme {
        ProductSearchContent(
            uiState = UiState(
                query = "Celular",
                screenState = UiState.ScreenState.ConnectionError
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSearchScreenServerError() {
    MLTechTestTheme {
        ProductSearchContent(
            uiState = UiState(
                query = "Celular",
                screenState = UiState.ScreenState.ServerError
            ),
            onItemClick = {}
        )
    }
}