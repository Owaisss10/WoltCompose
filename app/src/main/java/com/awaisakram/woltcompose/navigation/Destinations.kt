package com.awaisakram.woltcompose.navigation

sealed class Destination(
    val route: String,
) {

    data object Cities : Destination("cities")

    data object Restaurants : Destination("restaurants")

    data object RestaurantDetails : Destination("restaurant_details")
}