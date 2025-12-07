package com.example.pokemonxdigimon.manager

import com.example.network.api.DigimonApi
import com.example.network.data.ApiResponseData
import com.example.network.data.digimon.DigimonDetailModel
import com.example.network.data.digimon.DigimonListModel
import com.example.network.imp.IHttpClient

class DigimonNetworkManager(
    private val digimonClient: IHttpClient
) {
    private val digimonApi: DigimonApi = digimonClient.create(DigimonApi::class.java)

    suspend fun getDigimonList(pageSize: Int, page: Int = 0): ApiResponseData<DigimonListModel>  {
        val response = digimonClient.sendRequest { digimonApi.getDigimonList(pageSize, page) }
        return response
    }

    suspend fun getDigimonByName(name: String): ApiResponseData<DigimonDetailModel> {
        val response = digimonClient.sendRequest { digimonApi.getDigimonByName(name) }
        return response
    }

    suspend fun getDigimonCount(): ApiResponseData<Int> {
        val response = digimonClient.sendRequest { digimonApi.getDigimonList(pageSize = 1, page = 0) }
        return ApiResponseData(
            data = response.data?.pageable?.totalElements,
            hasError = response.hasError,
            error = response.error
        )
    }
}