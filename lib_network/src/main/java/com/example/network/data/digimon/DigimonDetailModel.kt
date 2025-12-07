package com.example.network.data.digimon

data class DigimonDetailModel(
    val id: Int,
    val name: String,
    val images: List<DigimonImage>,
    val types: List<DigimonType>,
    val descriptions: List<DigimonDescription>
) {
    val imageUrl: String
        get() = images.firstOrNull()?.href ?: ""

    val englishDescription: String?
        get() = descriptions.firstOrNull { it.language.contains("en") }?.description
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