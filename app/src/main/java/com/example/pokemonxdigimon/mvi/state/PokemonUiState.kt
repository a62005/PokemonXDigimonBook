package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.base.BaseUiState

data class PokemonUiState(
    val pokemonList: List<SimplePokemonBean> = emptyList(),
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    override var error: String? = null
) : BaseUiState