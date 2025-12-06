package com.example.pokemonxdigimon.mvi.intent

import com.example.pokemonxdigimon.base.BaseIntent

sealed class PokemonDetailIntent : BaseIntent {
    data class LoadPokemonDetail(val pokemonId: Int) : PokemonDetailIntent()
}
