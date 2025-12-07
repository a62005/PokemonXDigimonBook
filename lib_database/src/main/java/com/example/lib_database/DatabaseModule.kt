package com.example.lib_database

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.invoke(androidContext()) }
    single { get<AppDatabase>().pokemonDao() }
    single { get<AppDatabase>().digimonDao() }
}
