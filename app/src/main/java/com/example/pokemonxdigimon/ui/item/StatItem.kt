package com.example.pokemonxdigimon.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 能力值項目組件
 */
@Composable
fun StatItem(
    name: String,
    value: Int,
    maxValue: Int = 300,
    color: Color = Color(0xFF4CAF50)
) {
    val progress = (value.toFloat() / maxValue).coerceIn(0f, 1f)
    val textOnRight = progress < 0.2f
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 能力名稱
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(80.dp)
        )
        
        // 進度條容器
        Box(
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
        ) {
            // 背景條（白色）
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
            )

            // 進度條
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
            
            // 數值文本
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (textOnRight) {
                    // 進度條區域（佔位）
                    Spacer(modifier = Modifier.fillMaxWidth(fraction = progress))
                    
                    // 文本在右邊（空白區域），黑色
                    Text(
                        text = "$value/$maxValue",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                } else {
                    // 文本在左邊（進度條內），白色
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = progress)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "$value/$maxValue",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
