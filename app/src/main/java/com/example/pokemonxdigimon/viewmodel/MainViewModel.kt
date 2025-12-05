package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonxdigimon.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.initPokemonData()
        }
    }
}
