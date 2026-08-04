package com.awaisakram.woltcompose.domain.repository

import com.awaisakram.woltcompose.domain.model.City

interface CityRepository {

    suspend fun getCities(): List<City>
}