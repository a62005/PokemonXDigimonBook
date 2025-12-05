package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemonxdigimon.ui.screen.DigimonScreen
import com.example.pokemonxdigimon.ui.screen.HomeScreen
import com.example.pokemonxdigimon.ui.screen.PokemonScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            exitTransition = { slideOutHorizontally { -it } + fadeOut() },
            popEnterTransition = { slideInHorizontally { -it } + fadeIn() }
        ) {
            HomeScreen(
                onPokemonClick = { navController.navigate(Screen.Pokemon.route) },
                onDigimonClick = { navController.navigate(Screen.Digimon.route) }
            )
        }
        
        composable(
            route = Screen.Pokemon.route,
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it } + fadeOut() }
        ) {
            PokemonScreen(onBackClick = { navController.popBackStack() })
        }
        
        composable(
            route = Screen.Digimon.route,
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it } + fadeOut() }
        ) {
            DigimonScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
