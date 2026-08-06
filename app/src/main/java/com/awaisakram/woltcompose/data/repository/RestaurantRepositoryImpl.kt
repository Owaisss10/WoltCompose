package com.awaisakram.woltcompose.data.repository

import com.awaisakram.woltcompose.data.remote.api.WoltApi
import com.awaisakram.woltcompose.data.remote.mapper.toDomain
import com.awaisakram.woltcompose.domain.model.Restaurant
import com.awaisakram.woltcompose.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val api: WoltApi,
) : RestaurantRepository {

    override suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
    ): List<Restaurant> {

        val response = api.getRestaurants(
            latitude = latitude,
            longitude = longitude,
        )

        val venueSection = response.sections.firstOrNull {
            it.template == "venue-vertical-list"
        } ?: return emptyList()



        return venueSection.items
            .mapNotNull { item ->
                item.venue?.toDomain(
                    imageUrl = item.image?.url.orEmpty()
                )
            }
    }
}