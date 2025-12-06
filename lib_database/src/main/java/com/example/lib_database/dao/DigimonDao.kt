package com.example.lib_database.dao

import androidx.room.Dao
import com.example.lib_database.entity.DigimonEntity

@Dao
abstract class DigimonDao: BaseDao<DigimonEntity>() {
    
}