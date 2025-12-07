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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lib_database.bean.ISimpleBean
import com.example.pokemonxdigimon.utils.ClickUtils

/**
 * 通用怪獸卡片組件
 * 支持 Pokemon 和 Digimon
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MonsterCard(
    monster: ISimpleBean,
    backgroundColor: Color,
    textColor: Color = Color.White,
    onClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedVisibilityScope? = null,
    shouldLoadImage: Boolean = true
) {
    val context = LocalContext.current

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
                        model = ImageRequest.Builder(context)
                            .data(monster.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = monster.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                            .alpha(if (shouldLoadImage) 1f else 0f)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "monster-image-${monster.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = monster.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "monster-name-${monster.id}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
                    )
                }
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(monster.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = monster.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                        .alpha(if (shouldLoadImage) 1f else 0f),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = monster.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
