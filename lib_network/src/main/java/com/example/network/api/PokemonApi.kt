package com.example.network.api

import com.example.network.data.pokemon.PokemonDetailModel
import com.example.network.data.pokemon.PokemonListModel
import com.example.network.imp.IApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi: IApi {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 30): Response<PokemonListModel>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailModel>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): Response<PokemonDetailModel>
}