package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.repository.MainRepository
import com.example.pokemonxdigimon.repository.PokemonRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
    private val pokemonRepo: PokemonRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            pokemonRepo.initPokemonData()
        }
    }
}
