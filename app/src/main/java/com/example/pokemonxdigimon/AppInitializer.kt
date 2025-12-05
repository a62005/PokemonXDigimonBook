package com.example.pokemonxdigimon

import android.content.Context
import androidx.startup.Initializer
import com.example.lib_database.DatabaseInitializer
import com.example.network.NetworkInitializer
import com.example.pokemonxdigimon.di.appModule
import org.koin.core.context.loadKoinModules

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        loadKoinModules(appModule)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(
            StartupInitializer::class.java,
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }
}