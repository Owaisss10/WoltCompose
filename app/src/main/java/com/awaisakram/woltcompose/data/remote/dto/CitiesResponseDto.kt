package com.awaisakram.woltcompose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitiesResponseDto(
    val results: List<CityDto>,
)