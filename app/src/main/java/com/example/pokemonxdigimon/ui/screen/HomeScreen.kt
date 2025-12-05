package com.example.pokemonxdigimon.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.pokemonxdigimon.R

@Composable
fun HomeScreen(
    onPokemonClick: () -> Unit,
    onDigimonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPokemonClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.pokemon).toUpperCase(Locale.current))
        }
        
        Button(
            onClick = onDigimonClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.digimon).toUpperCase(Locale.current))
        }
    }
}
