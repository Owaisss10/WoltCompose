package com.awaisakram.woltcompose.data.remote.dto.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingDto(

    @SerialName("lat")
    val latitude: String,

    @SerialName("lon")
    val longitude: String,
)