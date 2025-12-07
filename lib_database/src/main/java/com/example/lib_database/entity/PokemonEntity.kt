package com.example.lib_database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lib_database.bean.IDetailBean
import com.example.lib_database.bean.ISimpleBean

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val name: String,
    override val imageUrl: String,
    val height: Int,
    val weight: Int,
    override val types: List<String>,
    override val description: String? = null,
    @Embedded
    val stat: Stat
): IDetailBean {

    companion object {
        fun getImageUrl(id: Int): String {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        }
    }
    val mainType: String
        get() = types.firstOrNull() ?: "unknown"
    
    val weightInKg: Double
        get() = weight / 10.0
    
    val heightInM: Double
        get() = height / 10.0
}

data class Stat(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val exp: Int
)

data class SimplePokemonBean(
    override val id: Int,
    override val name: String,
    override val imageUrl: String,
    val types: List<String>
): ISimpleBean {
    val mainType: String
        get() = types.firstOrNull() ?: "unknown"
}

