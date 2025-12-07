package com.example.pokemonxdigimon.repository

import com.example.lib_database.dao.PokemonDao
import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.SimplePokemonBean
import com.example.lib_database.entity.Stat
import com.example.network.data.ApiResponseData
import com.example.pokemonxdigimon.manager.PokemonNetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val pokemonDao: PokemonDao,
    private val pokemonNetworkManager: PokemonNetworkManager
) {

    companion object {
        private const val PAGE_SIZE = 30
    }

    var maxCount = 0
        private set
    
    fun observePokemonList(): Flow<List<SimplePokemonBean>> {
        return pokemonDao.observeSimplePokemonList()
    }

    suspend fun initPokemonData() {
        withContext(ioDispatcher) {
            launch {
                setMaxCount()
            }
            launch {
                val localSize = pokemonDao.getSize()
                if (localSize == 0) {
                    loadMorePokemon(0)
                }
            }
        }
    }

    private suspend fun setMaxCount() {
        pokemonNetworkManager.getPokemonCount().data?.let {
            maxCount = it
        }
    }
    
    suspend fun loadMorePokemon(offset: Int): ApiResponseData<Unit> {
        return withContext(ioDispatcher) {
            // 獲取 Pokemon 列表
            val listResponse = pokemonNetworkManager.getPokemonList(offset = offset, limit = PAGE_SIZE)
            
            if (listResponse.hasError) {
                return@withContext ApiResponseData(
                    data = null,
                    hasError = true,
                    error = listResponse.error
                )
            }

            listResponse.data?.let { data ->
                // 根據 results 中的 URL 獲取每個 Pokemon 的詳細資料
                for (pokemonResult in data.results) {
                    val name = pokemonResult.name
                    val existing = pokemonDao.getPokemonByName(pokemonResult.name)
                    if (existing == null) {
                        val detailResponse = pokemonNetworkManager.getPokemonByName(name)
                        if (detailResponse.hasError) {
                            return@withContext ApiResponseData(
                                data = null,
                                hasError = true,
                                error = detailResponse.error
                            )
                        }
                        detailResponse.data?.let { detail ->
                            val entity = PokemonEntity(
                                id = detail.id,
                                name = detail.name,
                                height = detail.height,
                                weight = detail.weight,
                                types = detail.types.map { it.pokemonTypeName.name },
                                stat = Stat(
                                    hp = detail.stats.find { it.pokemonStatName.name == "hp" }?.baseStat ?: 0,
                                    attack = detail.stats.find { it.pokemonStatName.name == "attack" }?.baseStat ?: 0,
                                    defense = detail.stats.find { it.pokemonStatName.name == "defense" }?.baseStat ?: 0,
                                    speed = detail.stats.find { it.pokemonStatName.name == "speed" }?.baseStat ?: 0,
                                    exp = detail.baseExperience
                                )
                            )
                            pokemonDao.insert(entity)
                        }
                    }
                }
            }
            ApiResponseData(data = Unit, hasError = false, error = null)
        }
    }
}
