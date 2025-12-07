package com.example.kotlin_wstep

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Body

interface MistralAPIService {
    @POST("v1/chat/completions")
    suspend fun getCompletions(
        @Header("Authorization") token: String,
        @Body body: MistralAPIRequest,
    ): MistralAPIResponse
}