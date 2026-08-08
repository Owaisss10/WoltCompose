package com.awaisakram.woltcompose.presentation.cities

import com.awaisakram.woltcompose.domain.model.City

data class CitiesUiState(
    /** Cities matching the current [query], already sorted for display. */
    val cities: List<City> = emptyList(),
    /** Size of the unfiltered set, so the UI can show "x of y". */
    val totalCityCount: Int = 0,
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
