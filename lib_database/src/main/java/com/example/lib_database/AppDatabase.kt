package com.example.lib_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lib_database.converter.Converters
import com.example.lib_database.dao.DigimonDao
import com.example.lib_database.dao.PokemonDao
import com.example.lib_database.entity.DigimonEntity
import com.example.lib_database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class, DigimonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        fun invokeTestDatabase(context: Context) =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase.db")
                .build()
    }

    abstract fun pokemonDao() : PokemonDao
    abstract fun digimonDao() : DigimonDao
}
