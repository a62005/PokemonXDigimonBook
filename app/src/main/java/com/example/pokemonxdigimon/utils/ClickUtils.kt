package com.example.pokemonxdigimon.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 防抖動點擊工具
 * 針對每個 UI 元素單獨防抖，不同 UI 可以快速互相點擊
 */
object ClickUtils {
    private val clickTimeMap = mutableMapOf<Int, Long>()
    private const val CLICK_THROTTLE_TIME = 500L

    private fun canClick(key: Int): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastClickTime = clickTimeMap[key] ?: 0L
        
        if (currentTime - lastClickTime > CLICK_THROTTLE_TIME) {
            clickTimeMap[key] = currentTime
            return true
        }
        return false
    }

    fun onSingleClick(action: () -> Unit) {
        val key = action.hashCode()
        if (canClick(key)) {
            action()
        }
    }
}

fun Modifier.singleClick(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        ClickUtils.onSingleClick(onClick)
    }
}
