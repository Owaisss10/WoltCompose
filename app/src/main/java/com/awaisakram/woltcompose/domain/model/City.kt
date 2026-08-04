package com.awaisakram.woltcompose.domain.model

data class City(
    val id: String,
    val name: String,
    val slug: String,
    val latitude: Double,
    val longitude: Double,
)