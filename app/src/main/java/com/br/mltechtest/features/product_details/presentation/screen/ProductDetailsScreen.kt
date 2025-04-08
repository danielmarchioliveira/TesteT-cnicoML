package com.br.mltechtest.features.product_details.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.br.mltechtest.core.designsystem.components.TopBar
import com.br.mltechtest.core.designsystem.theme.MLTechTestTheme
import com.br.mltechtest.features.product_details.presentation.viewmodel.ProductDetailsViewModel
import com.br.mltechtest.features.product_details.presentation.viewmodel.UiState

@Composable
fun ProductDetailsScreen(
    productId: String,
    onNavigateToBack: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(productId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Detalhes do produto",
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        ProductDetailsContent(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ProductDetailsContent(
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    when (uiState.screenState) {
        UiState.ScreenState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        UiState.ScreenState.ConnectionError -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sem conexão com a internet.")
            }
        }

        UiState.ScreenState.ServerError -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Erro ao carregar detalhes.")
            }
        }

        UiState.ScreenState.Loaded -> {
            val product = uiState.product
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                val imageSectionHeight = getImageHeight()

                if (uiState.product.imagesUrl.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageSectionHeight),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        items(product.imagesUrl) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Imagem do produto",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
private fun getImageHeight(): Dp {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    return (screenHeightDp * 0.3f).dp
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSearchScreenServerError() {
    MLTechTestTheme {
        ProductDetailsContent(
            uiState = UiState(
                product = UiState.Product(name = "teste", imagesUrl = emptyList()),
                screenState = UiState.ScreenState.ServerError
            ),
        )
    }
}