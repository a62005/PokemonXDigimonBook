package com.example.pokemonxdigimon.mvi.state

import com.example.lib_database.entity.DigimonEntity
import com.example.lib_database.entity.PokemonEntity
import com.example.pokemonxdigimon.base.BaseUiState

data class DigimonDetailUiState(
    val digimon: DigimonEntity? = null,
    val isLoading: Boolean = false,
    override var error: String? = null
) : BaseUiState
