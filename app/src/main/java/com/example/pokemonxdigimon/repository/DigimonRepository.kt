package com.example.pokemonxdigimon.repository

import com.example.lib_database.dao.DigimonDao
import com.example.lib_database.entity.DigimonEntity
import com.example.lib_database.entity.SimpleDigimonBean
import com.example.network.data.ApiResponseData
import com.example.pokemonxdigimon.manager.DigimonNetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DigimonRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val digimonDao: DigimonDao,
    private val digimonNetworkManager: DigimonNetworkManager
) {

    companion object {
        private const val PAGE_SIZE = 30
    }

    var maxCount = 0
        private set

    fun observeDigimonList(): Flow<List<SimpleDigimonBean>> {
        return digimonDao.observeSimpleDigimonList()
    }

    suspend fun initDigimonData() {
        withContext(ioDispatcher) {
            launch {
                setMaxCount()
            }
            launch {
                val localSize = digimonDao.getSize()
                if (localSize == 0) {
                    loadMoreDigimon(0)
                }
            }
        }
    }

    private suspend fun setMaxCount() {
        digimonNetworkManager.getDigimonCount().data?.let {
            maxCount = it
        }
    }

    private suspend fun getNextPage(): Int {
        return withContext(ioDispatcher) {
            val curSize = digimonDao.getSize()
            curSize / PAGE_SIZE
        }
    }

    suspend fun loadMoreDigimon(): ApiResponseData<Unit> {
        val currentPage = getNextPage()
        return loadMoreDigimon(currentPage)
    }

    suspend fun loadMoreDigimon(page: Int): ApiResponseData<Unit> {
        return withContext(ioDispatcher) {
            // 獲取 Pokemon 列表
            val listResponse = digimonNetworkManager.getDigimonList(pageSize = PAGE_SIZE, page = page)

            if (listResponse.hasError) {
                return@withContext ApiResponseData(
                    data = null,
                    hasError = true,
                    error = listResponse.error
                )
            }

            listResponse.data?.let { data ->
                // 根據 results 中的 URL 獲取每個 Pokemon 的詳細資料
                for (digimonContent in data.content) {
                    val name = digimonContent.name
                    val existing = digimonDao.getDigimonByName(name)
                    if (existing == null) {
                        val detailResponse = digimonNetworkManager.getDigimonByName(name)
                        if (detailResponse.hasError) {
                            return@withContext ApiResponseData(
                                data = null,
                                hasError = true,
                                error = detailResponse.error
                            )
                        }
                        detailResponse.data?.let { detail ->
                            val entity = DigimonEntity(
                                id = detail.id,
                                name = detail.name,
                                imageUrl = detail.imageUrl,
                                types = detail.types.map { it.type },
                                description = detail.englishDescription
                            )
                            digimonDao.insert(entity)
                        }
                    }
                }
            }
            ApiResponseData(data = Unit, hasError = false, error = null)
        }
    }
}