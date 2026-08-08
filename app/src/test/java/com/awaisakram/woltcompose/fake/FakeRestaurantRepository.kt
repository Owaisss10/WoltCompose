package com.awaisakram.woltcompose.fake

import com.awaisakram.woltcompose.domain.model.Restaurant
import com.awaisakram.woltcompose.domain.repository.RestaurantRepository
import kotlinx.coroutines.CompletableDeferred

class FakeRestaurantRepository : RestaurantRepository {

    private val result = CompletableDeferred<List<Restaurant>>()

    /** Coordinates the last call was made with, so tests can assert they are forwarded. */
    var requestedLatitude: Double? = null
        private set

    var requestedLongitude: Double? = null
        private set

    override suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
    ): List<Restaurant> {
        requestedLatitude = latitude
        requestedLongitude = longitude
        return result.await()
    }

    fun succeedWith(restaurants: List<Restaurant>) {
        result.complete(restaurants)
    }

    fun failWith(error: Throwable) {
        result.completeExceptionally(error)
    }
}
