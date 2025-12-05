package com.example.pokemonxdigimon.di

import com.example.pokemonxdigimon.manager.NetworkManager
import com.example.pokemonxdigimon.repository.MainRepository
import com.example.pokemonxdigimon.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::MainRepository)
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
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
