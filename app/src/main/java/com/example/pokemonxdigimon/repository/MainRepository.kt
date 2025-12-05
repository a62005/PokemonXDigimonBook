package com.example.pokemonxdigimon.repository

import com.example.lib_database.AppDatabase
import com.example.lib_database.entity.PokemonEntity
import com.example.lib_database.entity.Stat
import com.example.pokemonxdigimon.manager.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MainRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val database: AppDatabase,
    private val networkManager: NetworkManager
) {

    companion object {
        private const val INIT_SIZE = 20
    }
    private val pokemonDao = database.pokemonDao()
    
    suspend fun initPokemonList() {
        return withContext(ioDispatcher) {
            // 检查本地是否有数据
            val localSize = pokemonDao.getSize()
            if (localSize == 0) {
                val pokemonList = mutableListOf<PokemonEntity>()
                for (id in 1..INIT_SIZE) {
                    val response = networkManager.getPokemonById(id)
                    if (!response.hasError && response.data != null) {
                        response.data?.let {  detail ->
                            val entity = PokemonEntity(
                                id = detail.id,
                                name = detail.name,
                                height = detail.height,
                                weight = detail.weight,
                                types = detail.types.map { it.type.name },
                                stat = Stat(
                                    hp = detail.stats.find { it.stat.name == "hp" }?.baseStat ?: 0,
                                    attack = detail.stats.find { it.stat.name == "attack" }?.baseStat ?: 0,
                                    defense = detail.stats.find { it.stat.name == "defense" }?.baseStat ?: 0,
                                    speed = detail.stats.find { it.stat.name == "speed" }?.baseStat ?: 0,
                                    exp = detail.baseExperience
                                )
                            )
                            pokemonList.add(entity)
                        }
                    }
                }
                if (pokemonList.isNotEmpty()) {
                    pokemonDao.insert(pokemonList)
                }
            }
        }
    }
}
