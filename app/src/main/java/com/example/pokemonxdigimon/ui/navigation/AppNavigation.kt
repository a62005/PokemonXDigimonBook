package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pokemonxdigimon.ui.screen.DigimonScreen
import com.example.pokemonxdigimon.ui.screen.HomeScreen
import com.example.pokemonxdigimon.ui.screen.PokemonDetailScreen
import com.example.pokemonxdigimon.ui.screen.PokemonScreen

/**
 * 應用程式導航配置
 * 定義所有導航目的地、動畫和內容
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavGraph(
        navController = navController,
        startDestination = Screen.Home.route,
        destinations = listOf(
            // Home 頁面
            NavDestination(
                route = Screen.Home.route,
                exitTransition = NavTransitions.slideOutToLeft,
                popEnterTransition = NavTransitions.slideInFromLeft,
                content = {
                    HomeScreen(
                        onPokemonClick = { navController.navigate(Screen.Pokemon.route) },
                        onDigimonClick = { navController.navigate(Screen.Digimon.route) }
                    )
                }
            ),
            
            // Pokemon 列表頁面
            NavDestination(
                route = Screen.Pokemon.route,
                enterTransition = NavTransitions.slideInFromRight,
                exitTransition = NavTransitions.noExitTransition,
                popEnterTransition = NavTransitions.noTransition,
                popExitTransition = NavTransitions.slideOutToRight,
                content = {
                    PokemonScreen(
                        onPokemonClick = { pokemonId ->
                            navController.navigate(Screen.PokemonDetail.createRoute(pokemonId))
                        },
                        onBackClick = { navController.popBackStack() },
                        sharedTransitionScope = getSharedTransitionScope(),
                        animatedContentScope = getAnimatedVisibilityScope()
                    )
                }
            ),
            
            // Pokemon 詳情頁面
            NavDestination(
                route = Screen.PokemonDetail.route,
                enterTransition = NavTransitions.noTransition,
                exitTransition = NavTransitions.noExitTransition,
                popEnterTransition = NavTransitions.noTransition,
                popExitTransition = NavTransitions.noExitTransition,
                content = { backStackEntry ->
                    backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull()?.let { pokemonId ->
                        PokemonDetailScreen(
                            pokemonId = pokemonId,
                            onBackClick = { navController.popBackStack() },
                            sharedTransitionScope = getSharedTransitionScope(),
                            animatedContentScope = getAnimatedVisibilityScope()
                        )
                    }
                }
            ),
            
            // Digimon 頁面
            NavDestination(
                route = Screen.Digimon.route,
                enterTransition = NavTransitions.slideInFromRight,
                popExitTransition = NavTransitions.slideOutToRight,
                content = {
                    DigimonScreen(onBackClick = { navController.popBackStack() })
                }
            )
        )
    )
}
