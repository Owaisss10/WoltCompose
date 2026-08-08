package com.awaisakram.woltcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.awaisakram.woltcompose.presentation.cities.CitiesScreen
import com.awaisakram.woltcompose.presentation.restaurants.RestaurantsScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CitiesRoute,
    ) {

        composable<CitiesRoute> {
            CitiesScreen(
                onCityClick = { city ->
                    navController.navigate(
                        RestaurantsRoute(
                            cityName = city.name,
                            latitude = city.latitude,
                            longitude = city.longitude,
                        )
                    )
                },
            )
        }

        composable<RestaurantsRoute> { backStackEntry ->
            RestaurantsScreen(
                route = backStackEntry.toRoute<RestaurantsRoute>(),
            )
        }
    }
}
