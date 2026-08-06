package com.awaisakram.woltcompose.presentation.restaurants

import com.awaisakram.woltcompose.domain.model.Restaurant

data class RestaurantsUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val query: String = "",
    val error: String? = null,
)