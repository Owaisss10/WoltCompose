package com.awaisakram.woltcompose.data.remote.api

import com.awaisakram.woltcompose.data.remote.dto.CitiesResponseDto
import retrofit2.http.GET

interface CitiesApi {

    @GET("v1/cities")
    suspend fun getCities(): CitiesResponseDto
}