package com.awaisakram.woltcompose.presentation.search

import com.awaisakram.woltcompose.domain.model.Location

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val location: Location? = null,
    val error: String? = null,
)