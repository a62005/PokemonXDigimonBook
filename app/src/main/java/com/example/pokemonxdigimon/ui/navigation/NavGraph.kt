package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemonxdigimon.ui.screen.DigimonScreen
import com.example.pokemonxdigimon.ui.screen.HomeScreen
import com.example.pokemonxdigimon.ui.screen.PokemonScreen
import com.example.pokemonxdigimon.viewmodel.PokemonViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
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
            val pokemonViewModel: PokemonViewModel = koinViewModel()
            val uiState by pokemonViewModel.uiState.collectAsState()
            
            PokemonScreen(
                uiState = uiState,
                onIntent = pokemonViewModel::handleIntent,
                onBackClick = { navController.popBackStack() }
            )
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
