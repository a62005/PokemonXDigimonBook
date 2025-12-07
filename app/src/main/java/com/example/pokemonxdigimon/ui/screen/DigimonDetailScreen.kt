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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lib_database.entity.DigimonEntity
import com.example.pokemonxdigimon.base.ErrorHandler
import com.example.pokemonxdigimon.mvi.intent.DigimonDetailIntent
import com.example.lib_database.entity.SimpleDigimonBean
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.ui.item.TypeItem
import com.example.pokemonxdigimon.utils.ClickUtils
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

    when {
        uiState.isLoading && uiState.digimon == null -> {
            // Loading 狀態
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.digimon != null -> {
            // 顯示詳情
            DigimonDetailScreenContent(
                digimon = uiState.digimon!!,
                onBackClick = onBackClick,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DigimonDetailScreenContent(
    digimon: DigimonEntity,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Color.DarkGray)
    ) {
        // 白色背景區域（帶圓角）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                // 返回按鈕
                IconButton(
                    onClick = { ClickUtils.onSingleClick(onBackClick) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                with(sharedTransitionScope) {
                    // Digimon 圖片
                    AsyncImage(
                        model = digimon.imageUrl,
                        contentDescription = digimon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(12.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "monster-image-${digimon.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        // 詳細資訊區域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                with(sharedTransitionScope) {
                    // Digimon 名稱
                    Text(
                        text = digimon.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "monster-name-${digimon.id}"),
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
                    val types = digimon.type
                    if (types.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_type),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    } else {
                        types.forEachIndexed { index, type ->
                            TypeItem(type = type)
                            if (index < types.size - 1) {
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    }
                }
                if (digimon.description.isNotEmpty()) {
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
                        text = digimon.description,
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
