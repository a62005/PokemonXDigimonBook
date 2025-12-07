package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lib_database.entity.PokemonEntity
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.base.ErrorHandler
import com.example.pokemonxdigimon.mvi.intent.PokemonDetailIntent
import com.example.pokemonxdigimon.ui.item.InfoItem
import com.example.pokemonxdigimon.ui.item.StatItem
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

    val backgroundColor = uiState.pokemon?.let {
        Color(ColorUtils.getTypeColor(it.getMainType()))
    } ?: Color.DarkGray

    MonsterDetailScreen(
        monster = uiState.pokemon,
        isLoading = uiState.isLoading,
        backgroundColor = backgroundColor,
        backButtonTint = Color.White,
        onBackClick = onBackClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        additionalContent = { pokemon ->
            PokemonAdditionalContent(pokemon)
        }
    )
}

@Composable
private fun PokemonAdditionalContent(pokemon: PokemonEntity) {
    // 身高體重
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoItem(
            label = stringResource(R.string.weight),
            value = "${pokemon.weightInKg} KG"
        )
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
