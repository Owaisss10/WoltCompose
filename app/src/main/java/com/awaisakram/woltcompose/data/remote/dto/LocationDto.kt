package com.awaisakram.woltcompose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val coordinates: List<Double>,
)