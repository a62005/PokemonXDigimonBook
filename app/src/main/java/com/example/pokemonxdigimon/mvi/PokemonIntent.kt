package com.example.pokemonxdigimon.mvi

sealed class PokemonIntent {
    data object LoadInitialData : PokemonIntent()
    data object LoadMoreData : PokemonIntent()
}
