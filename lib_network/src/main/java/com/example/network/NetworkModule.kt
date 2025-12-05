package com.example.network

import com.example.network.imp.IHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    single<IHttpClient>(named("pokemon")) {
        val baseUrl = BuildConfig.POKEMON_BASE_URL
        HttpClientImp.Builder(baseUrl)
            .setTimeout(1000L)
            .enableLog(BuildConfig.DEBUG)
            .build()
    }
//    single<IHttpClient>(named("digimon")) {
//        val baseUrl = BuildConfig.POKEMON_BASE_URL
//        HttpClientImp.Builder(baseUrl)
//            .setTimeout(1000L)
//            .enableLog(BuildConfig.DEBUG_MODE)
//            .build()
//    }
}