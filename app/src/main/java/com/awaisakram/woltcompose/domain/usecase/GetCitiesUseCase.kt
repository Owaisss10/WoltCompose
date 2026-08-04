package com.awaisakram.woltcompose.domain.usecase

import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.repository.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository,
) {

    suspend operator fun invoke(): List<City> {
        return repository.getCities()
    }
}