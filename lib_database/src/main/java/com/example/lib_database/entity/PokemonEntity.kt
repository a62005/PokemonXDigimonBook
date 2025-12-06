package com.example.lib_database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    @Embedded
    val stat: Stat
) {
    val mainType: String
        get() = types.firstOrNull() ?: "unknown"

    val imageUrl: String
        get() = getImageUrl(id)
}

data class Stat(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val exp: Int
)

data class SimplePokemonBean(
    val id: Int,
    val name: String,
    val types: List<String>
) {
    val mainType: String
        get() = types.firstOrNull() ?: "unknown"
    
    val imageUrl: String
        get() = getImageUrl(id)
}

private fun getImageUrl(id: Int) = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"