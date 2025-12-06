package com.example.pokemonxdigimon.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal 用於傳遞 SharedTransitionScope 和 AnimatedVisibilityScope
 */
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val LocalAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

/**
 * 獲取當前的 SharedTransitionScope
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun getSharedTransitionScope(): SharedTransitionScope {
    return LocalSharedTransitionScope.current
        ?: throw IllegalStateException("SharedTransitionScope not found")
}

/**
 * 獲取當前的 AnimatedVisibilityScope
 */
@Composable
fun getAnimatedVisibilityScope(): AnimatedVisibilityScope {
    return LocalAnimatedVisibilityScope.current
        ?: throw IllegalStateException("AnimatedVisibilityScope not found")
}
