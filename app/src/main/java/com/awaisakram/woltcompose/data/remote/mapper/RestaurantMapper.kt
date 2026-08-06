package com.awaisakram.woltcompose.data.remote.mapper

import com.awaisakram.woltcompose.data.remote.dto.restaurants.VenueDto
import com.awaisakram.woltcompose.domain.model.Restaurant

fun VenueDto.toDomain(
    imageUrl: String,
): Restaurant =
    Restaurant(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        deliveryTime = estimate,
        priceRange = priceRange,
        tags = tags,
        address = address,
    )