package com.awaisakram.woltcompose.data.repository

import com.awaisakram.woltcompose.data.remote.api.WoltApi
import com.awaisakram.woltcompose.data.remote.dto.restaurants.RestaurantItemDto
import com.awaisakram.woltcompose.data.remote.dto.restaurants.RestaurantSectionDto
import com.awaisakram.woltcompose.data.remote.dto.restaurants.RestaurantsResponseDto
import com.awaisakram.woltcompose.data.remote.dto.restaurants.VenueDto
import com.awaisakram.woltcompose.data.remote.dto.restaurants.VenueImageDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RestaurantRepositoryImplTest {

    private class FakeWoltApi(
        private val response: RestaurantsResponseDto,
    ) : WoltApi {
        override suspend fun getRestaurants(latitude: Double, longitude: Double) = response
    }

    @Test
    fun `only the venue list section is mapped`() = runTest {
        val repository = RestaurantRepositoryImpl(
            FakeWoltApi(
                RestaurantsResponseDto(
                    sections = listOf(
                        section("banner", listOf(item("1", "Promoted banner"))),
                        section("venue-vertical-list", listOf(item("2", "Hesburger"))),
                    ),
                ),
            ),
        )

        val restaurants = repository.getRestaurants(60.17, 24.94)

        assertEquals(listOf("Hesburger"), restaurants.map { it.name })
    }

    @Test
    fun `items without a venue are skipped`() = runTest {
        val repository = RestaurantRepositoryImpl(
            FakeWoltApi(
                RestaurantsResponseDto(
                    sections = listOf(
                        section(
                            "venue-vertical-list",
                            listOf(
                                RestaurantItemDto(image = null, venue = null),
                                item("1", "Hesburger"),
                            ),
                        ),
                    ),
                ),
            ),
        )

        val restaurants = repository.getRestaurants(60.17, 24.94)

        assertEquals(1, restaurants.size)
    }

    @Test
    fun `the image url is taken from the item rather than the venue`() = runTest {
        val repository = RestaurantRepositoryImpl(
            FakeWoltApi(
                RestaurantsResponseDto(
                    sections = listOf(
                        section("venue-vertical-list", listOf(item("1", "Hesburger", "https://img/1.png"))),
                    ),
                ),
            ),
        )

        val restaurants = repository.getRestaurants(60.17, 24.94)

        assertEquals("https://img/1.png", restaurants.single().imageUrl)
    }

    @Test
    fun `a missing venue section yields no restaurants`() = runTest {
        val repository = RestaurantRepositoryImpl(
            FakeWoltApi(RestaurantsResponseDto(sections = listOf(section("banner", emptyList())))),
        )

        assertTrue(repository.getRestaurants(60.17, 24.94).isEmpty())
    }

    private fun section(template: String, items: List<RestaurantItemDto>) =
        RestaurantSectionDto(template = template, items = items)

    private fun item(id: String, name: String, imageUrl: String = "") = RestaurantItemDto(
        image = VenueImageDto(url = imageUrl),
        venue = VenueDto(id = id, name = name),
    )
}
