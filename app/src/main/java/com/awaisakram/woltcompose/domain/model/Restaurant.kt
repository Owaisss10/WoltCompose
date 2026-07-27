package com.awaisakram.woltcompose.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val shortDescription: String,
    val imageUrl: String,
    val rating: Double,
)