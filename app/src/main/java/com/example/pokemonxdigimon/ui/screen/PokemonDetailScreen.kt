package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lib_database.entity.PokemonEntity
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.base.ErrorHandler
import com.example.pokemonxdigimon.mvi.intent.PokemonDetailIntent
import com.example.pokemonxdigimon.ui.item.InfoItem
import com.example.pokemonxdigimon.ui.item.StatItem
import com.example.pokemonxdigimon.ui.item.TypeItem
import com.example.pokemonxdigimon.ui.model.StatConfigs
import com.example.pokemonxdigimon.utils.ColorUtils
import com.example.pokemonxdigimon.viewmodel.PokemonDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailScreen(
    pokemonId: Int?,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val viewModel: PokemonDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // 載入 Pokemon 詳細資訊
    LaunchedEffect(pokemonId) {
        pokemonId?.let {
            viewModel.handleIntent(PokemonDetailIntent.LoadPokemonDetail(it))
        }
    }

    // 通用錯誤處理
    ErrorHandler(
        uiState = uiState,
        onClearError = { viewModel.handleIntent(null) }
    )

    when {
        uiState.isLoading && uiState.pokemon == null -> {
            // Loading 狀態
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.pokemon != null -> {
            // 顯示詳情
            PokemonDetailScreenContent(
                pokemon = uiState.pokemon!!,
                onBackClick = onBackClick,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PokemonDetailScreenContent(
    pokemon: PokemonEntity,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val backgroundColor = Color(ColorUtils.getTypeColor(pokemon.mainType))
    val imageUrl = pokemon.imageUrl

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // 有顏色的背景區域（帶圓角）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                // 返回按鈕
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                with(sharedTransitionScope) {
                    // Pokemon 圖片
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(12.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "pokemon-image-${pokemon.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }

        // 詳細資訊區域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                with(sharedTransitionScope) {
                    // Pokemon 名稱
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "pokemon-name-${pokemon.id}"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
                    )
                }

                // TYPE 列表
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    pokemon.types.forEachIndexed { index, type ->
                        TypeItem(type = type)
                        if (index < pokemon.types.size - 1) {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }

                // 身高體重
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 體重
                    InfoItem(
                        label = stringResource(R.string.weight),
                        value = "${pokemon.weightInKg} KG"
                    )

                    // 身高
                    InfoItem(
                        label = stringResource(R.string.height),
                        value = "${pokemon.heightInM} M"
                    )
                }

                // Base Stats 標題
                Text(
                    text = stringResource(R.string.base_stats),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 16.dp)
                )

                // 能力值列表
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatConfigs.defaultStats.forEach { statConfig ->
                        StatItem(
                            name = stringResource(statConfig.nameResId),
                            value = statConfig.getValue(pokemon.stat),
                            color = colorResource(statConfig.colorResId)
                        )
                    }
                }
            }
        }
    }
}

