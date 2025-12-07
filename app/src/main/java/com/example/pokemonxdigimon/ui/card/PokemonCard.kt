package com.example.pokemonxdigimon.ui.card

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.ui.theme.PokemonXDigimonTheme
import com.example.pokemonxdigimon.utils.ColorUtils

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonCard(
    simplePokemonBean: SimplePokemonBean,
    onClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedVisibilityScope? = null,
    shouldLoadImage: Boolean = true
) {
    val backgroundColor = Color(ColorUtils.getTypeColor(simplePokemonBean.getMainType()))
    
    MonsterCard(
        monster = simplePokemonBean,
        backgroundColor = backgroundColor,
        textColor = Color.White,
        onClick = onClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        shouldLoadImage = shouldLoadImage
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    PokemonXDigimonTheme {
        PokemonCard(
            simplePokemonBean = SimplePokemonBean(
                id = 25,
                name = "Pikachu",
                imageUrl = PokemonEntity.getImageUrl(25),
                types = listOf("electric")
            ),
            {},
            null,
            null
        )
    }
}
