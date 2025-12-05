package com.example.lib_database

import android.content.Context
import androidx.startup.Initializer
import org.koin.core.context.loadKoinModules

class DatabaseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        loadKoinModules(databaseModule)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}
