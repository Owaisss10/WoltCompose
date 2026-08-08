package com.awaisakram.woltcompose.data.remote.mapper

import com.awaisakram.woltcompose.data.remote.dto.CityDto
import com.awaisakram.woltcompose.data.remote.dto.LocationDto
import org.junit.Assert.assertEquals
import org.junit.Test

class CityMapperTest {

    /**
     * GeoJSON orders coordinates as [longitude, latitude], which is the reverse of how
     * they are usually written. Swapping them silently sends every restaurant query to
     * the wrong place, so it is worth pinning down.
     */
    @Test
    fun `coordinates are read in GeoJSON order`() {
        val dto = CityDto(
            id = "1",
            name = "Helsinki",
            slug = "helsinki",
            location = LocationDto(coordinates = listOf(24.94, 60.17)),
        )

        val city = dto.toDomain()

        assertEquals(60.17, city.latitude, 0.001)
        assertEquals(24.94, city.longitude, 0.001)
    }

    @Test
    fun `identifying fields are carried across`() {
        val dto = CityDto(
            id = "abc",
            name = "Helsinki",
            slug = "helsinki",
            location = LocationDto(coordinates = listOf(24.94, 60.17)),
        )

        val city = dto.toDomain()

        assertEquals("abc", city.id)
        assertEquals("Helsinki", city.name)
        assertEquals("helsinki", city.slug)
    }
}
