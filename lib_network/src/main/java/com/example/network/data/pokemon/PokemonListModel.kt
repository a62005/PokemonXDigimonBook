package com.example.network.data.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonListModel(
    val count: Int,
    @SerializedName("next")
    val nextUrl: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)