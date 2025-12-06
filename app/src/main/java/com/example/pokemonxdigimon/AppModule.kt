package com.example.pokemonxdigimon

import com.example.pokemonxdigimon.manager.NetworkManager
import com.example.pokemonxdigimon.repository.MainRepository
import com.example.pokemonxdigimon.repository.PokemonDetailRepository
import com.example.pokemonxdigimon.repository.PokemonRepository
import com.example.pokemonxdigimon.viewmodel.MainViewModel
import com.example.pokemonxdigimon.viewmodel.PokemonDetailViewModel
import com.example.pokemonxdigimon.viewmodel.PokemonViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::MainRepository)
    factoryOf(::PokemonRepository)
    factoryOf(::PokemonDetailRepository)
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::PokemonViewModel)
    viewModelOf(::PokemonDetailViewModel)
}

val utilsModule = module {
    single {
        Dispatchers.IO
    }
    factory {
        NetworkManager(
            get(named("pokemon"))
        )
    }
}


val appModule = listOf(
    utilsModule,
    repositoryModule,
    viewModelModule
)
