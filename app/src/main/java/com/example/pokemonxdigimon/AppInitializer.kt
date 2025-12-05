package com.example.pokemonxdigimon

import android.content.Context
import androidx.startup.Initializer
import com.example.network.NetworkInitializer

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(
            StartupInitializer::class.java,
            NetworkInitializer::class.java
        )
    }
}