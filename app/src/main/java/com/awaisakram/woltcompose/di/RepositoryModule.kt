package com.awaisakram.woltcompose.di

import com.awaisakram.woltcompose.data.repository.CityRepositoryImpl
import com.awaisakram.woltcompose.data.repository.RestaurantRepositoryImpl
import com.awaisakram.woltcompose.domain.repository.CityRepository
import com.awaisakram.woltcompose.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityRepository(
        repository: CityRepositoryImpl,
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        repository: RestaurantRepositoryImpl,
    ): RestaurantRepository
}