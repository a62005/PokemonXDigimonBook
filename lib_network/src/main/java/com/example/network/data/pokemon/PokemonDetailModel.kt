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
    val type: PokemonTypeName
)

data class PokemonTypeName(
    val name: String
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: PokemonStatName
)

data class PokemonStatName(
    val name: String
)