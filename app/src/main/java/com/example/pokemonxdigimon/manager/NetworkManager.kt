package com.example.pokemonxdigimon.manager

import com.example.network.api.PokemonApi
import com.example.network.data.ApiResponseData
import com.example.network.data.pokemon.PokemonDetailModel
import com.example.network.imp.IHttpClient

class NetworkManager(
    private val pokemonClient: IHttpClient
) {
    private val pokemonApi: PokemonApi = pokemonClient.create(PokemonApi::class.java)

    suspend fun getPokemonById(id: Int): ApiResponseData<PokemonDetailModel> {
        val response = pokemonClient.sendRequest { pokemonApi.getPokemonDetail(id) }
        return response
    }
}