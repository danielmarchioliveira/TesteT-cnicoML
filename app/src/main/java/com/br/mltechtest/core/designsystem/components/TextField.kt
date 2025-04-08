package com.br.mltechtest.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.mltechtest.core.designsystem.theme.MLTechTestTheme

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("CustomTextField"),
            label = label?.let { { Text(it) } },
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = errorMessage != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true
        )
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextField() {
    MLTechTestTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TextField(
                value = "",
                onValueChange = {},
                label = "Buscar",
                placeholder = "Digite algo..."
            )

            TextField(
                value = "Produto",
                onValueChange = {},
                label = "Buscar",
                placeholder = "Digite algo..."
            )

            TextField(
                value = "Erro de validação",
                onValueChange = {},
                label = "Buscar",
                errorMessage = "Você precisa digitar algo"
            )

            TextField(
                value = "",
                onValueChange = {},
                label = "Buscar",
                placeholder = "Digite algo...",
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                }
            )

            TextField(
                value = "",
                onValueChange = {},
                label = "Buscar",
                placeholder = "Digite algo...",
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                }
            )
        }
    }
}