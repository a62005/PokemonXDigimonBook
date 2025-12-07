package com.example.pokemonxdigimon.mvi.intent

import com.example.pokemonxdigimon.base.BaseIntent

sealed class DigimonIntent : BaseIntent {
    data object LoadMoreData : DigimonIntent()
}
