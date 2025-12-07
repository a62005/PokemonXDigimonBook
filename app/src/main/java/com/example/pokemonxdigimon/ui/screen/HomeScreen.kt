package com.example.pokemonxdigimon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemonxdigimon.R
import com.example.pokemonxdigimon.utils.singleClick

@Composable
fun HomeScreen(
    onPokemonClick: () -> Unit,
    onDigimonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Pokemon 區域（上半部）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .singleClick(onClick = onPokemonClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo_pokemon),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                contentScale = ContentScale.Fit
            )
        }
        
        // 分隔線
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.Black)
        )
        
        // Digimon 區域（下半部）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .singleClick(onClick = onDigimonClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo_digimon),
                contentDescription = "Digimon",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
