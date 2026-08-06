package com.awaisakram.woltcompose.data.remote.dto.restaurants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VenueDto(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("short_description")
    val description: String = "",

    @SerialName("estimate_range")
    val estimate: String = "",

    @SerialName("address")
    val address: String = "",

    @SerialName("price_range")
    val priceRange: Int? = null,

    @SerialName("tags")
    val tags: List<String> = emptyList(),
)