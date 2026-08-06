package com.awaisakram.woltcompose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: String,
    val name: String,
    val slug: String,
    val latitude: Double,
    val longitude: Double,
) : Parcelable