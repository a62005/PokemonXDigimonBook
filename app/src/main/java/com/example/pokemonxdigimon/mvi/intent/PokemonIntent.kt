package com.example.pokemonxdigimon.mvi.intent

import com.example.pokemonxdigimon.base.BaseIntent

sealed class PokemonIntent: BaseIntent {
    data object LoadMoreData : PokemonIntent()
}