package com.awaisakram.woltcompose.domain.usecase

import com.awaisakram.woltcompose.domain.model.Restaurant
import com.awaisakram.woltcompose.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository,
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): List<Restaurant> {
        return repository.getRestaurants(
            latitude = latitude,
            longitude = longitude,
        )
    }
}