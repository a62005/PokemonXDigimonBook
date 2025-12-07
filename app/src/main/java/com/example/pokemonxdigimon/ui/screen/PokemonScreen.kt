package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.lib_database.bean.ISimpleBean
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.mvi.intent.PokemonIntent
import com.example.pokemonxdigimon.ui.card.MonsterCard
import com.example.pokemonxdigimon.ui.navigation.getAnimatedVisibilityScope
import com.example.pokemonxdigimon.ui.navigation.getSharedTransitionScope
import com.example.pokemonxdigimon.utils.ColorUtils
import com.example.pokemonxdigimon.viewmodel.PokemonViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonScreen(
    onMonsterClick: (ISimpleBean) -> Unit,
    onBackClick: () -> Unit
) {

    // TODO 如需要可移至AppNavigation，與DigimonScreen合併
    val pokemonViewModel: PokemonViewModel = koinViewModel()
    val uiState by pokemonViewModel.uiState.collectAsState()

    MonsterListScreen(
        title = stringResource(R.string.pokemon),
        uiState = uiState,
        onIntent = pokemonViewModel::handleIntent,
        onBackClick = onBackClick,
        onMonsterClick = { monster -> onMonsterClick(monster) },
        sharedTransitionScope = getSharedTransitionScope(),
        animatedContentScope = getAnimatedVisibilityScope(),
        loadMoreIntent = PokemonIntent.LoadMoreData,
        renderCard = { monster, onClick, sharedTransitionScope, animatedContentScope ->
            val backgroundColor = Color(ColorUtils.getTypeColor(monster.getMainType()))
            MonsterCard(
                monster = monster,
                backgroundColor = backgroundColor,
                textColor = Color.White,
                onClick = onClick,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )
}
