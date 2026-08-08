package com.awaisakram.woltcompose.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.usecase.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {

    // Sources of truth. The visible list is derived from these, never stored alongside them.
    private val cities = MutableStateFlow<List<City>>(emptyList())
    private val query = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<CitiesUiState> =
        combine(cities, query, isLoading, error) { cities, query, isLoading, error ->
            CitiesUiState(
                cities = cities.filter { it.name.contains(query, ignoreCase = true) },
                totalCityCount = cities.size,
                query = query,
                isLoading = isLoading,
                error = error,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MILLIS),
            initialValue = CitiesUiState(),
        )

    init {
        loadCities()
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun retry() {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                // Sorted once here rather than on every keystroke downstream.
                cities.value = getCitiesUseCase().sortedBy { it.name }
            } catch (exception: CancellationException) {
                // Cancellation is not a failure — it must propagate to keep
                // structured concurrency intact.
                throw exception
            } catch (exception: Exception) {
                error.value = exception.message ?: "Something went wrong"
            } finally {
                isLoading.value = false
            }
        }
    }

    private companion object {
        const val SUBSCRIPTION_TIMEOUT_MILLIS = 5_000L
    }
}
