package com.example.lib_database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.lib_database.entity.PokemonEntity

@Dao
abstract class PokemonDao: BaseDao<PokemonEntity>() {

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    abstract suspend fun getSize(): Int
}