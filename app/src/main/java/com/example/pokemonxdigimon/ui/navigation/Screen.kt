package com.example.pokemonxdigimon.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Pokemon : Screen("pokemon")
    data object PokemonDetail : Screen("pokemon/{pokemonId}") {
        fun createRoute(pokemonId: Int) = "pokemon/$pokemonId"
    }
    data object Digimon : Screen("digimon")
    data object DigimonDetail : Screen("digimon/{digimonId}/{digimonName}/{digimonImageUrl}/{digimonType}") {
        fun createRoute(digimonId: Int, digimonName: String, digimonImageUrl: String, digimonType: String) = 
            "digimon/$digimonId/$digimonName/$digimonImageUrl/$digimonType"
    }
}
