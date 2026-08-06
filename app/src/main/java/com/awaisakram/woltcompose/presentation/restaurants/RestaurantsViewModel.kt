package com.awaisakram.woltcompose.presentation.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.model.Restaurant
import com.awaisakram.woltcompose.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
) : ViewModel() {

    private var allRestaurants: List<Restaurant> = emptyList()

    private val _uiState = MutableStateFlow(RestaurantsUiState())

    val uiState: StateFlow<RestaurantsUiState> =
        _uiState.asStateFlow()


    fun loadRestaurants(city: City) {

        viewModelScope.launch {

            _uiState.value = RestaurantsUiState(
                isLoading = true
            )

            try {

                val restaurants = getRestaurantsUseCase(
                    latitude = city.latitude,
                    longitude = city.longitude
                )

                allRestaurants = restaurants

                _uiState.value = RestaurantsUiState(
                    restaurants = restaurants
                )

            } catch (exception: Exception) {

                _uiState.value = RestaurantsUiState(
                    error = exception.message
                        ?: "Unable to load restaurants"
                )
            }
        }
    }


    fun onQueryChanged(query: String) {

        val filteredRestaurants =
            if (query.isBlank()) {
                allRestaurants
            } else {
                allRestaurants.filter { restaurant ->

                    restaurant.name.contains(
                        query,
                        ignoreCase = true
                    ) ||
                            restaurant.tags.any {
                                it.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }
                }
            }


        _uiState.value = _uiState.value.copy(
            query = query,
            restaurants = filteredRestaurants,
        )
    }
}