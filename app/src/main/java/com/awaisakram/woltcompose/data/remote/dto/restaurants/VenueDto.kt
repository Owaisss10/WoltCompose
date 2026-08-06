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

    @SerialName("estimate")
    val estimate: String = "",

    @SerialName("image")
    val image: VenueImageDto? = null,
)