package com.example.network.imp

import androidx.annotation.WorkerThread
import com.example.network.data.ApiResponseData
import retrofit2.Response

interface IHttpClient {
    fun<T: IApi> create(service: Class<T>): T

    suspend fun <T> sendRequest(
        @WorkerThread request: suspend () -> Response<T>,
    ) : ApiResponseData<T>
}