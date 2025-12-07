package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.mvi.intent.PokemonIntent
import com.example.pokemonxdigimon.ui.card.PokemonCard
import com.example.pokemonxdigimon.viewmodel.PokemonViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonScreen(
    onPokemonClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val pokemonViewModel: PokemonViewModel = koinViewModel()
    val uiState by pokemonViewModel.uiState.collectAsState()

    MonsterListScreen(
        title = stringResource(R.string.pokemon),
        uiState = uiState,
        onIntent = pokemonViewModel::handleIntent,
        onBackClick = onBackClick,
        onMonsterClick = { pokemon -> onPokemonClick(pokemon.id) },
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        loadMoreIntent = PokemonIntent.LoadMoreData,
        renderCard = { monster, onClick, sharedScope, animatedScope, shouldLoadImage ->
            PokemonCard(
                simplePokemonBean = monster,
                onClick = onClick,
                sharedTransitionScope = sharedScope,
                animatedContentScope = animatedScope,
                shouldLoadImage = shouldLoadImage
            )
        }
    )
}
