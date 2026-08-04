package com.awaisakram.woltcompose.presentation.cities

import com.awaisakram.woltcompose.domain.model.City

data class CitiesUiState(
    val cities: List<City> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)