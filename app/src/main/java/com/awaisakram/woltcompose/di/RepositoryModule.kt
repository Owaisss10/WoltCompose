package com.awaisakram.woltcompose.di

import com.awaisakram.woltcompose.data.repository.GeocodingRepositoryImpl
import com.awaisakram.woltcompose.domain.repository.GeocodingRepository
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
    abstract fun bindGeocodingRepository(
        repository: GeocodingRepositoryImpl,
    ): GeocodingRepository
}