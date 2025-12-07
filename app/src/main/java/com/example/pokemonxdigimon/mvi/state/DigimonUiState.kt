package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.SimpleDigimonBean
import com.example.pokemonxdigimon.base.BaseUiState

data class DigimonUiState(
    val digimonList: List<SimpleDigimonBean> = emptyList(),
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    override var error: String? = null
) : BaseUiState
