package com.awaisakram.woltcompose.fake

import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.domain.repository.CityRepository
import kotlinx.coroutines.CompletableDeferred

/**
 * Completion is driven by the test rather than returning immediately, so that
 * in-flight states (such as loading) are observable.
 */
class FakeCityRepository : CityRepository {

    private val result = CompletableDeferred<List<City>>()

    override suspend fun getCities(): List<City> = result.await()

    fun succeedWith(cities: List<City>) {
        result.complete(cities)
    }

    fun failWith(error: Throwable) {
        result.completeExceptionally(error)
    }
}
