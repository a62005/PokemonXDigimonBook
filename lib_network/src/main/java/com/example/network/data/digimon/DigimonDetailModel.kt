package com.example.network.data.digimon

data class DigimonDetailModel(
    val id: Int,
    val name: String,
    val images: List<DigimonImage>,
    val type: List<DigimonType>,
    val descriptions: List<DigimonDescription>
) {
    val imageUrl: String
        get() = images.firstOrNull()?.href ?: ""
}

data class DigimonType(
    val type: String
)

data class DigimonDescription(
    val language: String,
    val description: String
)

data class DigimonImage(
    val href: String
)