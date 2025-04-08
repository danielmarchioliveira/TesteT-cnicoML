package com.br.mltechtest.features.home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.mltechtest.core.designsystem.components.TextField
import com.br.mltechtest.core.designsystem.components.TopBar
import com.br.mltechtest.core.designsystem.theme.Typography
import com.br.mltechtest.features.home.presentation.viewmodel.HomeViewModel
import com.br.mltechtest.features.home.presentation.viewmodel.UiEvent
import com.br.mltechtest.features.home.presentation.viewmodel.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSearch: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToSearch -> {
                    onNavigateToSearch(event.query)
                }
            }
        }
    }

    Content(
        uiState = uiState,
        onValueChange = viewModel::onValueChange,
        onClearSearch = viewModel::onClearSearch,
        onSearchClick = viewModel::onButtonClick,
    )
}

@Composable
private fun Content(
    uiState: UiState,
    onValueChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(title = "Mercado Livre")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Qual produto você busca?",
                style = Typography.bodyLarge,
            )
            TextField(
                value = uiState.textFieldValue,
                onValueChange = { onValueChange(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick() }
                ),
                label = "Buscar",
                placeholder = "Digite algo...",
                trailingIcon = {
                    if (uiState.textFieldValue.isNotBlank()) {
                        IconButton(onClick = onClearSearch) {
                            Icon(Icons.Default.Close, contentDescription = "Limpar texto")
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    Content(
        uiState = UiState(

        ),
        onValueChange = {},
        onClearSearch = {},
        onSearchClick = {},
    )
}

