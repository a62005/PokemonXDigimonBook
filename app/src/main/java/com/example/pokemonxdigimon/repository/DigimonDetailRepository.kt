package com.example.pokemonxdigimon.repository

import com.example.lib_database.dao.DigimonDao
import com.example.lib_database.entity.DigimonEntity
import kotlinx.coroutines.flow.Flow

class DigimonDetailRepository(
    private val digimonDao: DigimonDao
) {

    fun observePokemonById(id: Int): Flow<DigimonEntity?> {
        return digimonDao.observeDigimonById(id)
    }
}