package com.example.pokemonxdigimon.mvi.intent

import com.example.pokemonxdigimon.base.BaseIntent

sealed class DigimonDetailIntent : BaseIntent {
    data class LoadDigimonDetail(val digimonId: Int) : DigimonDetailIntent()
}
