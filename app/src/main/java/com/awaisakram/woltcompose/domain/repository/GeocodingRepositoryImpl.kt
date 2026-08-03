package com.awaisakram.woltcompose.data.repository

import com.awaisakram.woltcompose.data.remote.api.GeocodingApi
import com.awaisakram.woltcompose.data.remote.mapper.toDomain
import com.awaisakram.woltcompose.domain.model.Location
import com.awaisakram.woltcompose.domain.repository.GeocodingRepository
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val api: GeocodingApi,
) : GeocodingRepository {

    override suspend fun search(
        query: String,
    ): Result<Location> {

        return runCatching {
            api.search(query)
                .first()
                .toDomain()
        }
    }
}