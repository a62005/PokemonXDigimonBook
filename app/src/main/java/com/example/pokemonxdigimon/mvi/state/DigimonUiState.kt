package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.SimpleDigimonBean
import com.example.pokemonxdigimon.base.BaseListUiState

data class DigimonUiState(
    override val monsterList: List<SimpleDigimonBean> = emptyList(),
    override val isLoadingMore: Boolean = false,
    override val hasMore: Boolean = true,
    override var error: String? = null
) : BaseListUiState<SimpleDigimonBean>
