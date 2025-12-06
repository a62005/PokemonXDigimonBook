package com.example.pokemonxdigimon.repository

import com.example.lib_database.AppDatabase
import com.example.lib_database.entity.SimplePokemonBean
import com.example.network.data.ApiResponseData
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
        private const val PAGE_SIZE = 30
    }
    
    private val pokemonDao = database.pokemonDao()
    
    fun observePokemonList(): Flow<List<SimplePokemonBean>> {
        return pokemonDao.observeSimplePokemonList()
    }

    suspend fun initPokemonData() {
        withContext(ioDispatcher) {
            val localSize = pokemonDao.getSize()
            if (localSize == 0) {
                loadMorePokemon(1)
            }
        }
    }
    
    suspend fun loadMorePokemon(startId: Int, count: Int = PAGE_SIZE): ApiResponseData<Unit> {
        return withContext(ioDispatcher) {
            for (id in startId until (startId + count)) {
                // 檢查是否已存在
                val existing = pokemonDao.getPokemonById(id)
                if (existing == null) {
                    val response = networkManager.getPokemonById(id)
                    if (response.hasError) {
                        return@withContext ApiResponseData(
                            data = null,
                            hasError = true,
                            error = response.error
                        )
                    }
                    response.data?.let { detail ->
                        val entity = PokemonMapper.toEntity(detail)
                        pokemonDao.insert(entity)
                    }
                }
            }
            ApiResponseData(data = Unit, hasError = false, error = null)
        }
    }
}
