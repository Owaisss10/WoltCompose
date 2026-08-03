package com.awaisakram.woltcompose.data.remote.mapper

import com.awaisakram.woltcompose.data.remote.dto.geocoding.GeocodingDto
import com.awaisakram.woltcompose.domain.model.Location

fun GeocodingDto.toDomain(): Location =
    Location(
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
    )