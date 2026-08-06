package com.awaisakram.woltcompose.data.repository

import com.awaisakram.woltcompose.data.remote.api.CitiesApi
import com.awaisakram.woltcompose.data.remote.mapper.toDomain
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val api: CitiesApi,
) : CityRepository {

    override suspend fun getCities(): List<City> {
        return api
            .getCities()
            .results
            .map { it.toDomain() }
    }
}