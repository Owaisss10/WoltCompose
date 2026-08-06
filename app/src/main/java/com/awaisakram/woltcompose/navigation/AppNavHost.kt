package com.awaisakram.woltcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.presentation.cities.CitiesScreen
import com.awaisakram.woltcompose.presentation.restaurants.RestaurantsScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Cities.route,
    ) {

        composable(Destination.Cities.route) {
            CitiesScreen(
                navController = navController,
            )
        }

        composable(Destination.Restaurants.route) {

            val city = navController.getSelectedCity()

            if (city != null) {
                RestaurantsScreen(
                    navController = navController,
                    city = city,
                )
            } else {
                navController.popBackStack()
            }
        }

        composable(Destination.RestaurantDetails.route) {
            // We'll implement this later
        }
    }
}