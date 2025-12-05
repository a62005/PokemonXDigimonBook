package com.example.pokemonxdigimon.repository

import com.example.lib_database.AppDatabase
import com.example.lib_database.entity.SimplePokemonBean
import com.example.pokemonxdigimon.manager.NetworkManager
import com.example.pokemonxdigimon.mapper.PokemonMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val database: AppDatabase,
    private val networkManager: NetworkManager
) {

    companion object {
        private const val PAGE_SIZE = 20
    }
    
    private val pokemonDao = database.pokemonDao()
    
    fun observePokemonList(): Flow<List<SimplePokemonBean>> {
        return pokemonDao.observeSimplePokemonList()
    }
    
    suspend fun loadMorePokemon(startId: Int, count: Int = PAGE_SIZE) {
        withContext(ioDispatcher) {
            for (id in startId until (startId + count)) {
                // 檢查是否已存在
                val existing = pokemonDao.getPokemonById(id)
                if (existing == null) {
                    val response = networkManager.getPokemonById(id)
                    if (!response.hasError && response.data != null) {
                        response.data?.let { detail ->
                            val entity = PokemonMapper.toEntity(detail)
                            pokemonDao.insert(entity)
                        }
                    }
                }
            }
        }
    }
}
