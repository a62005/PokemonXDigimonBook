package com.example.network

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.context.loadKoinModules

class NetworkInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        loadKoinModules(networkModule)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}