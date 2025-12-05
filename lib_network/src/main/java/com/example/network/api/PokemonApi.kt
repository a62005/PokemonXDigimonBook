package com.example.network.api

import com.example.network.data.pokemon.PokemonDetailModel
import com.example.network.imp.IApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi: IApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailModel>
}