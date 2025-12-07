package com.example.pokemonxdigimon.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lib_database.entity.DigimonEntity
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.base.ErrorHandler
import com.example.pokemonxdigimon.mvi.intent.DigimonDetailIntent
import com.example.pokemonxdigimon.viewmodel.DigimonDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DigimonDetailScreen(
    digimonId: Int?,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val viewModel: DigimonDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // 載入 Digimon 詳細資訊
    LaunchedEffect(digimonId) {
        digimonId?.let {
            viewModel.handleIntent(DigimonDetailIntent.LoadDigimonDetail(it))
        }
    }

    // 通用錯誤處理
    ErrorHandler(
        uiState = uiState,
        onClearError = { viewModel.handleIntent(null) }
    )

    MonsterDetailScreen(
        monster = uiState.digimon,
        isLoading = uiState.isLoading,
        backgroundColor = Color.White,
        backButtonTint = Color.Black,
        onBackClick = onBackClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        additionalContent = { digimon ->
            DigimonAdditionalContent(digimon)
        }
    )
}

@Composable
private fun DigimonAdditionalContent(digimon: DigimonEntity) {
    if (!digimon.description.isNullOrEmpty()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Text(
            text = stringResource(R.string.description),
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(
            text = digimon.description!!,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
    } else {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(
            text = stringResource(R.string.no_description),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
