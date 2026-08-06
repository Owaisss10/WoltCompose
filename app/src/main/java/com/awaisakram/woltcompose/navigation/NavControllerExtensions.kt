package com.awaisakram.woltcompose.navigation

import androidx.navigation.NavController
import com.awaisakram.woltcompose.domain.model.City

private const val SELECTED_CITY_KEY = "selected_city"

fun NavController.setSelectedCity(city: City) {
    currentBackStackEntry
        ?.savedStateHandle
        ?.set(
            SELECTED_CITY_KEY,
            city
        )
}


fun NavController.getSelectedCity(): City? {
    return previousBackStackEntry
        ?.savedStateHandle
        ?.get<City>(SELECTED_CITY_KEY)
}