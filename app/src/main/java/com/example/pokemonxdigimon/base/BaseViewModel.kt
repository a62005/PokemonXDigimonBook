package com.example.pokemonxdigimon.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T: BaseUiState>: ViewModel() {

    protected abstract val _uiState: MutableStateFlow<T>
    abstract val uiState: StateFlow<T>

    abstract fun handleIntent(intent: BaseIntent?)
}