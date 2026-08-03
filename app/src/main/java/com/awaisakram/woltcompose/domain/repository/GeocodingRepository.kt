package com.awaisakram.woltcompose.domain.repository

import com.awaisakram.woltcompose.domain.model.Location

interface GeocodingRepository {

    suspend fun search(
        query: String,
    ): Location
}