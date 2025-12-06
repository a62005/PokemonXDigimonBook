package com.example.network.data.digimon

import com.google.gson.annotations.SerializedName

data class DigimonListModel(
    val pageable: Pageable,
    val content: List<DigimonContent>
)

data class DigimonContent(
    val id: Int,
    val name: String,
    @SerializedName("href")
    val dataUrl: String
)

data class Pageable(
    val currentPage: Int,
    val elementsOnPage: Int,
    val totalElements: Int,
    val totalPages: Int,
    val nextPage: String?,
)