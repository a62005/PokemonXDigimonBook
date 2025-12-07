package com.example.pokemonxdigimon.utils

import android.graphics.Color
import java.util.Locale

object ColorUtils {

    fun getTypeColor(type: String?): Int {
        if (type == null) return Color.parseColor("#CCCCCC")
        val color = when (type.lowercase(Locale.getDefault())) {
            "grass" -> "#2CDAB1"
            "fire" -> "#F7706B"
            "water" -> "#58ABF6"
            "bug" -> "#2CDA90"
            "normal" -> "#7986CB"
            "poison" -> "#9F5BBA"
            "electric" -> "#FFE252"
            "ground" -> "#CA8179"
            "fairy" -> "#FF86AF"
            "fighting" -> "#F78C6B"
            "psychic" -> "#FFCE4B"
            "rock" -> "#955A54"
            "ghost" -> "#7C5BBA"
            "ice" -> "#58F1F6"
            "dragon" -> "#7A9A64"
            "dark" -> "#303943"
            "steel" -> "#4A425A"
            "flying" -> "#C379CB"
            else -> "#CCCCCC"
        }
        return Color.parseColor(color)
    }
}