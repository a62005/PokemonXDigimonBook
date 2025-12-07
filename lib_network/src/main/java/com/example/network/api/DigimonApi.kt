package com.example.network.api

import com.example.network.data.digimon.DigimonDetailModel
import com.example.network.data.digimon.DigimonListModel
import com.example.network.imp.IApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApi: IApi {
    @GET("digimon")
    suspend fun getDigimonList(@Query("pageSize") pageSize: Int = 30, @Query("page") page: Int = 0): Response<DigimonListModel>

    @GET("digimon/{name}")
    suspend fun getDigimonByName(@Path("name") name: String): Response<DigimonDetailModel>
}