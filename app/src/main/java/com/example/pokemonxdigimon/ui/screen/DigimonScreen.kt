package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.lib_database.entity.SimpleDigimonBean
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.mvi.intent.DigimonIntent
import com.example.pokemonxdigimon.ui.card.DigimonCard
import com.example.pokemonxdigimon.viewmodel.DigimonViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DigimonScreen(
    onDigimonClick: (SimpleDigimonBean) -> Unit = {},
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val digimonViewModel: DigimonViewModel = koinViewModel()
    val uiState by digimonViewModel.uiState.collectAsState()

    MonsterListScreen(
        title = stringResource(R.string.digimon),
        uiState = uiState,
        onIntent = digimonViewModel::handleIntent,
        onBackClick = onBackClick,
        onMonsterClick = onDigimonClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        loadMoreIntent = DigimonIntent.LoadMoreData,
        renderCard = { monster, onClick, sharedScope, animatedScope, shouldLoadImage ->
            DigimonCard(
                digimonBean = monster,
                onClick = onClick,
                sharedTransitionScope = sharedScope,
                animatedContentScope = animatedScope,
                shouldLoadImage = shouldLoadImage
            )
        }
    )
}
