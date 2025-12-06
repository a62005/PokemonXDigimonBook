package com.example.pokemonxdigimon.ui.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.lib_database.entity.Stat
import com.example.pokemonxdigimon.R

/**
 * Stat 配置數據類
 */
data class StatConfig(
    @StringRes val nameResId: Int,
    val getValue: (Stat) -> Int,
    @ColorRes val colorResId: Int
)

/**
 * 預定義的 Stat 配置列表
 */
object StatConfigs {
    val defaultStats = listOf(
        StatConfig(
            nameResId = R.string.hp,
            getValue = { it.hp },
            colorResId = R.color.stat_hp
        ),
        StatConfig(
            nameResId = R.string.attack,
            getValue = { it.attack },
            colorResId = R.color.stat_attack
        ),
        StatConfig(
            nameResId = R.string.defense,
            getValue = { it.defense },
            colorResId = R.color.stat_defense
        ),
        StatConfig(
            nameResId = R.string.speed,
            getValue = { it.speed },
            colorResId = R.color.stat_speed
        ),
        StatConfig(
            nameResId = R.string.exp,
            getValue = { it.exp },
            colorResId = R.color.stat_exp
        )
    )
}
