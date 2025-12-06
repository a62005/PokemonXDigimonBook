package com.example.pokemonxdigimon.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokemonxdigimon.utils.ColorUtils

/**
 * TYPE 標籤組件
 */
@Composable
fun TypeItem(type: String) {
    val typeColor = Color(ColorUtils.getTypeColor(type))
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(typeColor)
            .padding(horizontal = 40.dp, vertical = 4.dp)
    ) {
        Text(
            text = type.uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}