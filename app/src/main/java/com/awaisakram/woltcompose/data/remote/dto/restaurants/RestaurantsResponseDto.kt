package com.awaisakram.woltcompose.data.remote.dto.restaurants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponseDto(
    @SerialName("sections")
    val sections: List<RestaurantSectionDto>,
)