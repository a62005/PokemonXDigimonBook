package com.example.network.data.digimon

data class DigimonDetailModel(
    val id: Int,
    val name: String,
    val type: List<DigimonType>,
    val descriptions: List<DigimonDescription>
)

data class DigimonType(
    val type: String
)

data class DigimonDescription(
    val language: String,
    val description: String
)