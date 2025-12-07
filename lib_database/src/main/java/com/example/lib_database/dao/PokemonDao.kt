package com.example.lib_database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.SimplePokemonBean
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao: BaseDao<PokemonEntity>() {

    @Query("SELECT COUNT(*) FROM PokemonEntity")
    abstract suspend fun getSize(): Int
    
    @Query("SELECT id, name, imageUrl, types FROM PokemonEntity ORDER BY id ASC")
    abstract fun observeSimplePokemonList(): Flow<List<SimplePokemonBean>>
    
    @Query("SELECT * FROM PokemonEntity WHERE id = :id")
    abstract suspend fun getPokemonById(id: Int): PokemonEntity?

    @Query("SELECT * FROM PokemonEntity WHERE name = :name")
    abstract suspend fun getPokemonByName(name: String): PokemonEntity?
    
    @Query("SELECT * FROM PokemonEntity WHERE id = :id")
    abstract fun observePokemonById(id: Int): Flow<PokemonEntity?>
}