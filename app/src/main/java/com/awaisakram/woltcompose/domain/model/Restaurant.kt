package com.awaisakram.woltcompose.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val deliveryTime: String,
    val priceRange: Int?,
    val tags: List<String>,
    val address: String?,
)