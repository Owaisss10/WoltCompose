package com.awaisakram.woltcompose.domain.repository

import com.awaisakram.woltcompose.domain.model.Restaurant

interface RestaurantRepository {

    suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
    ): List<Restaurant>
}