package com.example.pokemonxdigimon.ui.card

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.ui.theme.PokemonXDigimonTheme
import com.example.pokemonxdigimon.utils.ClickUtils
import com.example.pokemonxdigimon.utils.ColorUtils

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonCard(
    simplePokemonBean: SimplePokemonBean,
    onClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedVisibilityScope? = null
) {
    val backgroundColor = Color(ColorUtils.getTypeColor(simplePokemonBean.mainType))
    
    Card(
        onClick = { ClickUtils.onSingleClick(onClick) },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (sharedTransitionScope != null && animatedContentScope != null) {
                with(sharedTransitionScope) {
                    AsyncImage(
                        model = simplePokemonBean.imageUrl,
                        contentDescription = simplePokemonBean.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "pokemon-image-${simplePokemonBean.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = simplePokemonBean.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "pokemon-name-${simplePokemonBean.id}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
                    )
                }
            } else {
                AsyncImage(
                    model = simplePokemonBean.imageUrl,
                    contentDescription = simplePokemonBean.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = simplePokemonBean.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
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
