package com.awaisakram.woltcompose.presentation.restaurants

import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.model.Restaurant
import com.awaisakram.woltcompose.domain.usecase.GetRestaurantsUseCase
import com.awaisakram.woltcompose.fake.FakeRestaurantRepository
import com.awaisakram.woltcompose.util.MainDispatcherRule
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class RestaurantsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeRestaurantRepository()

    private val helsinki = City(
        id = "1",
        name = "Helsinki",
        slug = "helsinki",
        latitude = 60.17,
        longitude = 24.94,
    )

    private fun createViewModel() =
        RestaurantsViewModel(GetRestaurantsUseCase(repository))

    @Test
    fun `city coordinates are forwarded to the repository`() = runTest {
        val viewModel = createViewModel()

        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)
        runCurrent()

        assertEquals(60.17, repository.requestedLatitude!!, 0.001)
        assertEquals(24.94, repository.requestedLongitude!!, 0.001)
    }

    @Test
    fun `loading is shown while restaurants are in flight`() = runTest {
        val viewModel = createViewModel()

        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)
        runCurrent()
        assertTrue(viewModel.uiState.value.isLoading)

        repository.succeedWith(listOf(restaurant("1", "Hesburger")))
        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `query filters restaurants by name`() = runTest {
        val viewModel = createViewModel()
        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)

        repository.succeedWith(
            listOf(restaurant("1", "Hesburger"), restaurant("2", "Taco Bell")),
        )
        advanceUntilIdle()

        viewModel.onQueryChanged("hes")

        assertEquals(listOf("Hesburger"), viewModel.uiState.value.restaurants.map { it.name })
    }

    @Test
    fun `query also matches tags`() = runTest {
        val viewModel = createViewModel()
        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)

        repository.succeedWith(
            listOf(
                restaurant("1", "Hesburger", tags = listOf("burger")),
                restaurant("2", "Noodle Story", tags = listOf("asian")),
            ),
        )
        advanceUntilIdle()

        viewModel.onQueryChanged("asian")

        assertEquals(listOf("Noodle Story"), viewModel.uiState.value.restaurants.map { it.name })
    }

    @Test
    fun `clearing the query restores every restaurant`() = runTest {
        val viewModel = createViewModel()
        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)

        repository.succeedWith(
            listOf(restaurant("1", "Hesburger"), restaurant("2", "Taco Bell")),
        )
        advanceUntilIdle()

        viewModel.onQueryChanged("hes")
        viewModel.onQueryChanged("")

        assertEquals(2, viewModel.uiState.value.restaurants.size)
    }

    @Test
    fun `failure surfaces an error`() = runTest {
        val viewModel = createViewModel()
        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)

        repository.failWith(IOException("No connection"))
        advanceUntilIdle()

        assertEquals("No connection", viewModel.uiState.value.error)
    }

    /** Regression test — see the equivalent test in `CitiesViewModelTest`. */
    @Test
    fun `cancellation is not reported as an error`() = runTest {
        val viewModel = createViewModel()
        viewModel.loadRestaurants(helsinki.latitude, helsinki.longitude)

        repository.failWith(CancellationException("Screen left"))
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.error)
    }

    private fun restaurant(
        id: String,
        name: String,
        tags: List<String> = emptyList(),
    ) = Restaurant(
        id = id,
        name = name,
        description = "",
        imageUrl = "",
        deliveryTime = "20-30",
        priceRange = 1,
        tags = tags,
        address = null,
    )
}
