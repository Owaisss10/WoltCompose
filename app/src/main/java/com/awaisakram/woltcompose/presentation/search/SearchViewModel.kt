package com.awaisakram.woltcompose.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awaisakram.woltcompose.domain.usecase.SearchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchLocation: SearchLocationUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun search() {

        val query = _uiState.value.query

        if (query.isBlank()) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            searchLocation(query)
                .onSuccess { location ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            location = location,
                        )
                    }
                }
                .onFailure { throwable ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "Unknown error",
                        )
                    }
                }
        }
    }
}