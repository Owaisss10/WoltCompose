package com.awaisakram.woltcompose.data.remote.dto.restaurants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantItemDto(
    @SerialName("venue")
    val venue: VenueDto? = null,
)