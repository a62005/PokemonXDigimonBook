package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * 通用導航圖
 * 只負責畫面切換，動畫和內容由外部定義
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    destinations: List<NavDestination>
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            destinations.forEach { destination ->
                composable(
                    route = destination.route,
                    enterTransition = destination.enterTransition,
                    exitTransition = destination.exitTransition,
                    popEnterTransition = destination.popEnterTransition,
                    popExitTransition = destination.popExitTransition
                ) { backStackEntry ->
                    CompositionLocalProvider(
                        LocalSharedTransitionScope provides this@SharedTransitionLayout,
                        LocalAnimatedVisibilityScope provides this@composable
                    ) {
                        destination.content(backStackEntry)
                    }
                }
            }
        }
    }
}
