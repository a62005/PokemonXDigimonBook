package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.base.BaseListUiState

data class PokemonUiState(
    override val monsterList: List<SimplePokemonBean> = emptyList(),
    override val isLoadingMore: Boolean = false,
    override val hasMore: Boolean = true,
    override var error: String? = null
) : BaseListUiState<SimplePokemonBean>
