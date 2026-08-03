//package com.awaisakram.woltcompose.data.remote.api
//
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface WoltApi {
//
//    @GET("v1/pages/restaurants")
//    suspend fun getRestaurants(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//    ): RestaurantResponseDto
//}
// TODO: FIX THIS!
package com.awaisakram.woltcompose.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WoltApi {

    @GET("v1/pages/restaurants")
    suspend fun getRestaurants(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    )
}