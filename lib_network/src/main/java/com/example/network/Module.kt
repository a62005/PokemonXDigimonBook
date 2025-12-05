package com.example.network

import com.example.network.imp.IHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    single<IHttpClient>(named("pokemon")) {
        val baseUrl = Config.POKEMON_BASE_URL
        HttpClientImp.Builder(baseUrl)
            .setTimeout(1000L)
            .build()
    }
    single<IHttpClient>(named("digimon")) {
        val baseUrl = Config.DIGIMON_BASE_URL
        HttpClientImp.Builder(baseUrl)
            .setTimeout(1000L)
            .build()
    }
}