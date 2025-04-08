package com.br.mltechtest.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onValueChange(value: String) {
        _uiState.update {
            it.copy(textFieldValue = value,)
        }
    }

    fun onClearSearch() {
        _uiState.update { UiState() }
    }

    fun onButtonClick() {
        if (_uiState.value.textFieldValue.isBlank()) return

        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateToSearch(_uiState.value.textFieldValue))
        }
    }
}