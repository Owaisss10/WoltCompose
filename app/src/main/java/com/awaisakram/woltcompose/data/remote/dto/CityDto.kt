package com.awaisakram.woltcompose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: String,
    val name: String,
    val slug: String,
    val location: LocationDto,
)