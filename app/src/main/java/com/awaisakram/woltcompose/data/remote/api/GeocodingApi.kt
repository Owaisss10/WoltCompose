package com.awaisakram.woltcompose.data.remote.api

import com.awaisakram.woltcompose.data.remote.dto.geocoding.GeocodingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("format") format: String = "jsonv2",
        @Query("limit") limit: Int = 1,
    ): List<GeocodingDto>
}