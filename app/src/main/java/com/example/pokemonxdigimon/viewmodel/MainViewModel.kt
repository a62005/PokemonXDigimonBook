package com.example.pokemonxdigimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<SimplePokemonBean>>(emptyList())
    val pokemonList: StateFlow<List<SimplePokemonBean>> = _pokemonList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initPokemonList()
        }
    }
}
