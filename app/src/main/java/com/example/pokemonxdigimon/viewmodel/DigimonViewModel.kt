package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.base.BaseIntent
import com.example.pokemonxdigimon.base.BaseViewModel
import com.example.pokemonxdigimon.mvi.intent.DigimonIntent
import com.example.pokemonxdigimon.mvi.state.DigimonUiState
import com.example.pokemonxdigimon.repository.DigimonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DigimonViewModel(private val repository: DigimonRepository) : BaseViewModel<DigimonUiState>() {
    override val _uiState = MutableStateFlow(DigimonUiState())
    override val uiState: StateFlow<DigimonUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeDigimonList().collect { list ->
                _uiState.update {
                    it.copy(
                        digimonList = list,
                        hasMore = if (it.hasMore && it.digimonList.isNotEmpty()) {
                            it.digimonList.size <= repository.maxCount
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
            is DigimonIntent.LoadMoreData -> loadMoreData()
            else -> clearError()
        }
    }

    private fun loadMoreData() {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMore) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true, error = null) }

            val result = repository.loadMoreDigimon()
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
