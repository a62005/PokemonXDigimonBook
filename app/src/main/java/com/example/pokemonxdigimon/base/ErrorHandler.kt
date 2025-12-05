package com.example.pokemonxdigimon.base

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

/**
 * 通用的錯誤處理組件
 * 監聽 UiState 的 error 欄位，當有錯誤時顯示 Toast 並清除錯誤
 */
@Composable
fun <T : BaseUiState> ErrorHandler(
    uiState: T,
    onClearError: () -> Unit
) {
    val context = LocalContext.current
    
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            onClearError()
        }
    }
}
