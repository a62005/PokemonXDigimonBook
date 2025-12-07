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
import com.example.lib_database.bean.IDetailBean
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.ui.item.TypeItem
import com.example.pokemonxdigimon.utils.ClickUtils

/**
 * 通用怪獸詳情頁面
 * 支持 Pokemon 和 Digimon
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun <T : IDetailBean> MonsterDetailScreen(
    monster: T?,
    isLoading: Boolean,
    backgroundColor: Color,
    backButtonTint: Color,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    additionalContent: @Composable (T) -> Unit = {}
) {
    when {
        isLoading && monster == null -> {
            // Loading 狀態
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        monster != null -> {
            // 顯示詳情
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .navigationBarsPadding()
            ) {
                // 有顏色的背景區域（帶圓角）
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
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
                            onClick = { ClickUtils.onSingleClick(onBackClick) },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = backButtonTint
                            )
                        }

                        with(sharedTransitionScope) {
                            // 怪獸圖片
                            AsyncImage(
                                model = monster.imageUrl,
                                contentDescription = monster.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(12.dp)
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(key = "monster-image-${monster.id}"),
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
                            // 怪獸名稱
                            Text(
                                text = monster.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(key = "monster-name-${monster.id}"),
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
                            val types = monster.types
                            if (types.isNullOrEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_type),
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
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

                        // 額外內容（Pokemon 的 stats、Digimon 的 description 等）
                        additionalContent(monster)
                    }
                }
            }
        }
    }
}
