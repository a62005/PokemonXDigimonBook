package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.PokemonEntity
import com.example.pokemonxdigimon.base.BaseUiState

data class PokemonDetailUiState(
    val pokemon: PokemonEntity? = null,
    val isLoading: Boolean = false,
    override var error: String? = null
) : BaseUiState
