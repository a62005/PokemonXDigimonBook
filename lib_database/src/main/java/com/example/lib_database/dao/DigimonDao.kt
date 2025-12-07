package com.example.lib_database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.lib_database.entity.DigimonEntity
import com.example.lib_database.entity.SimpleDigimonBean
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DigimonDao: BaseDao<DigimonEntity>() {

    @Query("SELECT id, name, imageUrl FROM DigimonEntity ORDER BY id ASC")
    abstract fun observeSimpleDigimonList(): Flow<List<SimpleDigimonBean>>

    @Query("SELECT COUNT(*) FROM DigimonEntity")
    abstract suspend fun getSize(): Int

    @Query("SELECT * FROM DigimonEntity WHERE name = :name")
    abstract suspend fun getDigimonByName(name: String): DigimonEntity?
}