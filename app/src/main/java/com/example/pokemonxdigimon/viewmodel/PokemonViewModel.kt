package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.base.BaseIntent
import com.example.pokemonxdigimon.base.BaseViewModel
import com.example.pokemonxdigimon.mvi.intent.PokemonIntent
import com.example.pokemonxdigimon.mvi.state.ListDataUiState
import com.example.pokemonxdigimon.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repository: PokemonRepository
) : BaseViewModel<ListDataUiState>() {
    override val _uiState = MutableStateFlow(ListDataUiState())

    init {
        viewModelScope.launch {
            repository.observePokemonList().collect { list ->
                _uiState.update {
                    it.copy(
                        dataList = list,
                        hasMore = if (it.hasMore && it.dataList.isNotEmpty()) {
                            it.dataList.size <= repository.maxCount
                        } else {
                            true
                        }
                    )
                }
            }
        }
    }

    override fun handleIntent(intent: BaseIntent?) {
        when (intent) {
            is PokemonIntent.LoadMoreData -> loadMoreData()
            else -> clearError()
        }
    }

    private fun loadMoreData() {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMore) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true, error = null) }

            val offset = _uiState.value.dataList.size
            val result = repository.loadMorePokemon(offset)
            if (result.hasError) {
                _uiState.update { it.copy(error = result.error?.message) }
            }
            
            _uiState.update { it.copy(isLoadingMore = false) }
        }
    }
    
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
