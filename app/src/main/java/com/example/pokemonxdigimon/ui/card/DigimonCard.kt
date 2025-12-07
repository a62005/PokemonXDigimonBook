package com.example.pokemonxdigimon.ui.card

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.lib_database.entity.SimpleDigimonBean

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DigimonCard(
    digimonBean: SimpleDigimonBean,
    onClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedVisibilityScope? = null,
    shouldLoadImage: Boolean = true
) {
    MonsterCard(
        monster = digimonBean,
        backgroundColor = Color.White,
        textColor = Color.Black,
        onClick = onClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        shouldLoadImage = shouldLoadImage
    )
}
