package com.example.pokemonxdigimon.repository

import com.example.lib_database.dao.PokemonDao
import com.example.lib_database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

class PokemonDetailRepository(
    private val pokemonDao: PokemonDao
) {
    
    /**
     * 監聽指定 ID 的 Pokemon 詳細資訊
     */
    fun observePokemonById(pokemonId: Int): Flow<PokemonEntity?> {
        return pokemonDao.observePokemonById(pokemonId)
    }
}
