package com.awaisakram.woltcompose.data.remote.dto.restaurants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantSectionDto(
    @SerialName("template")
    val template: String,

    @SerialName("items")
    val items: List<RestaurantItemDto>,
)