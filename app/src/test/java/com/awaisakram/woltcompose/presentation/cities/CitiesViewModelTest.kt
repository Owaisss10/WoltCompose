package com.awaisakram.woltcompose.presentation.cities

import app.cash.turbine.test
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.usecase.GetCitiesUseCase
import com.awaisakram.woltcompose.fake.FakeCityRepository
import com.awaisakram.woltcompose.util.MainDispatcherRule
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class CitiesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeCityRepository()

    private fun createViewModel() =
        CitiesViewModel(GetCitiesUseCase(repository))

    /**
     * [CitiesViewModel.uiState] is shared with `WhileSubscribed`, so it only produces
     * values while something collects it.
     */
    private fun TestScope.collectUiState(viewModel: CitiesViewModel) {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }
    }

    @Test
    fun `cities are sorted alphabetically`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        repository.succeedWith(listOf(city("1", "Helsinki"), city("2", "Aachen"), city("3", "Berlin")))
        advanceUntilIdle()

        assertEquals(
            listOf("Aachen", "Berlin", "Helsinki"),
            viewModel.uiState.value.cities.map { it.name },
        )
    }

    @Test
    fun `loading is shown while cities are in flight and cleared afterwards`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        runCurrent()
        assertTrue(viewModel.uiState.value.isLoading)

        repository.succeedWith(listOf(city("1", "Helsinki")))
        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `query filters cities without changing the total count`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        repository.succeedWith(
            listOf(city("1", "Helsinki"), city("2", "Helsingborg"), city("3", "Berlin")),
        )
        advanceUntilIdle()

        viewModel.onQueryChanged("hels")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(listOf("Helsingborg", "Helsinki"), state.cities.map { it.name })
        assertEquals(3, state.totalCityCount)
    }

    @Test
    fun `query matching is case insensitive`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        repository.succeedWith(listOf(city("1", "Helsinki")))
        advanceUntilIdle()

        viewModel.onQueryChanged("HELSINKI")
        advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.cities.size)
    }

    @Test
    fun `clearing the query restores the full list`() = runTest {
        val viewModel = createViewModel()

        repository.succeedWith(listOf(city("1", "Helsinki"), city("2", "Berlin")))
        advanceUntilIdle()

        viewModel.uiState.test {
            // `stateIn` replays its initial value to a new subscriber before the
            // upstream `combine` starts producing.
            assertEquals(0, awaitItem().cities.size)
            assertEquals(2, awaitItem().cities.size)

            viewModel.onQueryChanged("berlin")
            assertEquals(listOf("Berlin"), awaitItem().cities.map { it.name })

            viewModel.onQueryChanged("")
            assertEquals(2, awaitItem().cities.size)
        }
    }

    @Test
    fun `failure surfaces an error and stops loading`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        repository.failWith(IOException("No connection"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("No connection", state.error)
        assertFalse(state.isLoading)
    }

    /**
     * Regression test: catching a bare [Exception] would swallow [CancellationException]
     * and report ordinary coroutine cancellation to the user as a failure.
     */
    @Test
    fun `cancellation is not reported as an error`() = runTest {
        val viewModel = createViewModel()
        collectUiState(viewModel)

        repository.failWith(CancellationException("Screen left"))
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.error)
    }

    private fun city(id: String, name: String) = City(
        id = id,
        name = name,
        slug = name.lowercase(),
        latitude = 0.0,
        longitude = 0.0,
    )
}
