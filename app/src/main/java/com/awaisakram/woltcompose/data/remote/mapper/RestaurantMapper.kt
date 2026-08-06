package com.awaisakram.woltcompose.data.remote.mapper

import com.awaisakram.woltcompose.data.remote.dto.restaurants.VenueDto
import com.awaisakram.woltcompose.domain.model.Restaurant

fun VenueDto.toDomain(): Restaurant =
    Restaurant(
        id = id,
        name = name,
        description = description,
        imageUrl = image?.url.orEmpty(),
        deliveryTime = estimate,
    )