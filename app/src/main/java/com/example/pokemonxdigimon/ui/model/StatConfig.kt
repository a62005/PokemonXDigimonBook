package com.example.pokemonxdigimon.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.lib_database.entity.Stat
import com.example.pokemonxdigimon.R

/**
 * Stat 配置數據類
 */
data class StatConfig(
    @StringRes val nameResId: Int,
    val getValue: (Stat) -> Int,
    val color: Color
)

/**
 * 預定義的 Stat 配置列表
 */
object StatConfigs {
    val defaultStats = listOf(
        StatConfig(
            nameResId = R.string.hp,
            getValue = { it.hp },
            color = Color(0xFFD33A48)
        ),
        StatConfig(
            nameResId = R.string.attack,
            getValue = { it.attack },
            color = Color(0xFFFFA42B)
        ),
        StatConfig(
            nameResId = R.string.defense,
            getValue = { it.defense },
            color = Color(0xFF068FE2)
        ),
        StatConfig(
            nameResId = R.string.speed,
            getValue = { it.speed },
            color = Color(0xFF91AFC6)
        ),
        StatConfig(
            nameResId = R.string.exp,
            getValue = { it.exp },
            color = Color(0xFF398D3C)
        )
    )
}
