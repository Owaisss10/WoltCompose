package com.awaisakram.woltcompose.di

import com.awaisakram.woltcompose.data.remote.api.CitiesApi
import com.awaisakram.woltcompose.data.remote.api.WoltApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header(
                        "User-Agent",
                        "WoltCompose/1.0 (awaisakram@example.com)"
                    )
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    @WoltRetrofit
    fun provideWoltRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://restaurant-api.wolt.com/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

    @Provides
    @Singleton
    fun provideWoltApi(
        @WoltRetrofit retrofit: Retrofit,
    ): WoltApi =
        retrofit.create(WoltApi::class.java)

    @Provides
    @Singleton
    fun provideCitiesApi(
        @WoltRetrofit retrofit: Retrofit,
    ): CitiesApi =
        retrofit.create(CitiesApi::class.java)
}