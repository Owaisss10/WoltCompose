package com.awaisakram.woltcompose.navigation

import kotlinx.serialization.Serializable

@Serializable
data object CitiesRoute

/**
 * Restaurants are looked up by coordinates, so the route carries the values the screen
 * actually needs.
 *
 * A destination that owns its arguments stays valid for as long as it is on the back
 * stack and can be restored after process death — unlike reading the selection back out
 * of a neighbouring back stack entry, which is only correct while that neighbour happens
 * to be in the expected position.
 */
@Serializable
data class RestaurantsRoute(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
)
