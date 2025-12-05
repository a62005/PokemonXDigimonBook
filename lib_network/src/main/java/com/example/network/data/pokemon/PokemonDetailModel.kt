package com.example.network.data.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonDetailModel(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
)

data class PokemonType(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: Stat
)

data class Stat(
    val name: String
)