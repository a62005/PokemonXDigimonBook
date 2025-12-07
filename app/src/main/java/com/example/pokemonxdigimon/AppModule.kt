package com.example.pokemonxdigimon

import com.example.pokemonxdigimon.manager.DigimonNetworkManager
import com.example.pokemonxdigimon.manager.PokemonNetworkManager
import com.example.pokemonxdigimon.repository.DigimonRepository
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
    factoryOf(::DigimonRepository)
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
        PokemonNetworkManager(
            get(named("pokemon"))
        )
    }
    factory {
        DigimonNetworkManager(
            get(named("digimon"))
        )
    }
}


val appModule = listOf(
    utilsModule,
    repositoryModule,
    viewModelModule
)
