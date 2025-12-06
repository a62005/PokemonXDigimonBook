package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import com.example.pokemonxdigimon.ui.screen.PokemonDetailScreen
import com.example.pokemonxdigimon.ui.screen.PokemonScreen
import com.example.pokemonxdigimon.viewmodel.PokemonViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    SharedTransitionLayout {
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
                exitTransition = { null },
                popEnterTransition = { null },
                popExitTransition = { slideOutHorizontally { it } + fadeOut() }
            ) {
                val pokemonViewModel: PokemonViewModel = koinViewModel()
                val uiState by pokemonViewModel.uiState.collectAsState()

                PokemonScreen(
                    uiState = uiState,
                    onIntent = pokemonViewModel::handleIntent,
                    onPokemonClick = { pokemon ->
                        navController.navigate(Screen.PokemonDetail.createRoute(pokemon.id))
                    },
                    onBackClick = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable(
                route = Screen.PokemonDetail.route,
                enterTransition = { null },
                exitTransition = { null },
                popEnterTransition = { null },
                popExitTransition = { null }
            ) { backStackEntry ->
                val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull()
                val pokemonViewModel: PokemonViewModel = koinViewModel()
                val uiState by pokemonViewModel.uiState.collectAsState()

                val pokemon = uiState.pokemonList.find { it.id == pokemonId }

                pokemon?.let {
                    PokemonDetailScreen(
                        pokemon = it,
                        onBackClick = { navController.popBackStack() },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }
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
}
