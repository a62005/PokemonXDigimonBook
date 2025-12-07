package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.repository.DigimonRepository
import com.example.pokemonxdigimon.repository.MainRepository
import com.example.pokemonxdigimon.repository.PokemonRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val pokemonRepo: PokemonRepository,
    private val digimonRepo: DigimonRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            launch {
                pokemonRepo.initPokemonData()
            }
            launch {
                digimonRepo.initDigimonData()
            }
        }
    }
}
