package com.awaisakram.woltcompose.domain.usecase

import com.awaisakram.woltcompose.domain.model.Location
import com.awaisakram.woltcompose.domain.repository.GeocodingRepository
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val repository: GeocodingRepository,
) {

    suspend operator fun invoke(
        query: String,
    ): Location {
        return repository.search(query)
    }
}