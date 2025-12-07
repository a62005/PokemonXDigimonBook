package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.base.BaseIntent
import com.example.pokemonxdigimon.base.BaseViewModel
import com.example.pokemonxdigimon.mvi.intent.PokemonDetailIntent
import com.example.pokemonxdigimon.mvi.state.PokemonDetailUiState
import com.example.pokemonxdigimon.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonDetailRepository
) : BaseViewModel<PokemonDetailUiState>() {
    
    override val _uiState = MutableStateFlow(PokemonDetailUiState())

    override fun handleIntent(intent: BaseIntent?) {
        when (intent) {
            is PokemonDetailIntent.LoadPokemonDetail -> loadPokemonDetail(intent.pokemonId)
            null -> clearError()
        }
    }
    
    /**
     * 載入 Pokemon 詳細資訊
     * 本地一定有資料，直接監聽資料庫變化
     */
    private fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // 監聽資料庫變化
            repository.observePokemonById(pokemonId).collect { pokemon ->
                _uiState.update { it.copy(pokemon = pokemon, isLoading = false) }
            }
        }
    }
    
    /**
     * 清除錯誤訊息
     */
    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
