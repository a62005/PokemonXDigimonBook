package com.example.network.data

data class SimplePokemonModel(
    val result: List<SimplePokemon>
)

data class SimplePokemon(
    val name: String,
    val url: String
)