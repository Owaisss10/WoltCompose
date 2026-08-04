package com.awaisakram.woltcompose.data.remote.mapper

import com.awaisakram.woltcompose.data.remote.dto.CityDto
import com.awaisakram.woltcompose.domain.model.City

fun CityDto.toDomain(): City =
    City(
        id = id,
        name = name,
        slug = slug,
        latitude = location.coordinates[1],
        longitude = location.coordinates[0],
    )