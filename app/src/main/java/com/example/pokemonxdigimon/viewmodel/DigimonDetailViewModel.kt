package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.base.BaseIntent
import com.example.pokemonxdigimon.base.BaseViewModel
import com.example.pokemonxdigimon.mvi.intent.DigimonDetailIntent
import com.example.pokemonxdigimon.mvi.state.DigimonDetailUiState
import com.example.pokemonxdigimon.repository.DigimonDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DigimonDetailViewModel(
    private val repo: DigimonDetailRepository
): BaseViewModel<DigimonDetailUiState>() {

    override val _uiState: MutableStateFlow<DigimonDetailUiState> = MutableStateFlow(DigimonDetailUiState())

    override fun handleIntent(intent: BaseIntent?) {
        when (intent) {
            is DigimonDetailIntent.LoadDigimonDetail -> loadDigimonDetail(intent.digimonId)
            null -> clearError()
        }
    }

    /**
     * 載入 Pokemon 詳細資訊
     * 本地一定有資料，直接監聽資料庫變化
     */
    private fun loadDigimonDetail(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // 監聽資料庫變化
            repo.observePokemonById(id).collect { digimon ->
                _uiState.update { it.copy(digimon = digimon, isLoading = false) }
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