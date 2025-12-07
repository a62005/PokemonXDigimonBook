package com.example.pokemonxdigimon.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T: BaseUiState>: ViewModel() {

    protected abstract val _uiState: MutableStateFlow<T>
    val uiState: StateFlow<T> get() = _uiState.asStateFlow()

    abstract fun handleIntent(intent: BaseIntent?)
}