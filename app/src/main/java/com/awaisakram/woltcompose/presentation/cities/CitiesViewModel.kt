package com.awaisakram.woltcompose.presentation.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.usecase.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitiesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            try {
                val cities = getCitiesUseCase()

                _uiState.update {
                    it.copy(
                        cities = cities,
                        isLoading = false,
                    )
                }
            } catch (exception: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Something went wrong",
                    )
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }
}