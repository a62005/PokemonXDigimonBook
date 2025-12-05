package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.mvi.PokemonIntent
import com.example.pokemonxdigimon.mvi.state.PokemonUiState
import com.example.pokemonxdigimon.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()
    
    private var currentMaxId = 0

    init {
        viewModelScope.launch {
            repository.observePokemonList().collect { list ->
                _uiState.update { it.copy(pokemonList = list) }
                if (list.isNotEmpty()) {
                    currentMaxId = list.maxOf { it.id }
                }
            }
        }
        handleIntent(PokemonIntent.LoadInitialData)
    }

    fun handleIntent(intent: PokemonIntent) {
        when (intent) {
            is PokemonIntent.LoadInitialData -> loadInitialData()
            is PokemonIntent.LoadMoreData -> loadMoreData()
        }
    }

    private fun loadInitialData() {
        if (_uiState.value.isInitialLoading) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isInitialLoading = true) }
            try {
                repository.loadMorePokemon(startId = 1, count = 20)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isInitialLoading = false) }
            }
        }
    }

    private fun loadMoreData() {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMore) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            try {
                val nextId = currentMaxId + 1
                repository.loadMorePokemon(startId = nextId, count = 20)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoadingMore = false) }
            }
        }
    }
}
