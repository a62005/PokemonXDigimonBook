package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

/**
 * 導航轉場動畫配置
 */
object NavTransitions {
    
    // 從右側滑入
    val slideInFromRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally { it } + fadeIn()
    }
    
    // 向右側滑出
    val slideOutToRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally { it } + fadeOut()
    }
    
    // 從左側滑入
    val slideInFromLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally { -it } + fadeIn()
    }
    
    // 向左側滑出
    val slideOutToLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally { -it } + fadeOut()
    }
    
    // 無動畫
    val noTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        null
    }
    
    val noExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        null
    }
}
