package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.SimplePokemonBean

data class PokemonUiState(
    val pokemonList: List<SimplePokemonBean> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    val error: UiError? = null
)