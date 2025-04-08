package com.br.mltechtest.features.home.presentation.viewmodel

sealed class UiEvent {
    data class NavigateToSearch(val query: String) : UiEvent()
}