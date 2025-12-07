package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.lib_database.bean.ISimpleBean
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.mvi.intent.DigimonIntent
import com.example.pokemonxdigimon.ui.card.MonsterCard
import com.example.pokemonxdigimon.ui.navigation.getAnimatedVisibilityScope
import com.example.pokemonxdigimon.ui.navigation.getSharedTransitionScope
import com.example.pokemonxdigimon.viewmodel.DigimonViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DigimonScreen(
    onMonsterClick: (ISimpleBean) -> Unit = {},
    onBackClick: () -> Unit
) {
    val digimonViewModel: DigimonViewModel = koinViewModel()
    val uiState by digimonViewModel.uiState.collectAsState()
    MonsterListScreen(
        title = stringResource(R.string.digimon),
        uiState = uiState,
        onIntent = digimonViewModel::handleIntent,
        onBackClick = onBackClick,
        onMonsterClick = onMonsterClick,
        sharedTransitionScope = getSharedTransitionScope(),
        animatedContentScope = getAnimatedVisibilityScope(),
        loadMoreIntent = DigimonIntent.LoadMoreData,
        renderCard = { monster, onClick, sharedTransitionScope, animatedContentScope ->
            MonsterCard(
                monster = monster,
                backgroundColor = Color.White,
                textColor = Color.Black,
                onClick = onClick,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )
}
