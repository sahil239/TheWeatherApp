package dev.sahildesai.theweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sahildesai.theweatherapp.data.api.WeatherApiService
import dev.sahildesai.theweatherapp.data.db.WeatherDao
import dev.sahildesai.theweatherapp.repository.IWeatherRepository
import dev.sahildesai.theweatherapp.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesWeatherRepository(
        apiService: WeatherApiService,
        weatherDao: WeatherDao
    ): IWeatherRepository = WeatherRepository(
        apiService,
        weatherDao
    )
}