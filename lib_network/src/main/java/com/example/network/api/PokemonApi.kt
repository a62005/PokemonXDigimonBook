package com.example.network.api

import com.example.network.data.SimplePokemonModel
import com.example.network.imp.IApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi: IApi {
    @GET("offset={offset}&limit=100")
    suspend fun getPokemonList(@Path("offset") offset: Int = 0): Response<SimplePokemonModel>
}