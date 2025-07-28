package dev.sahildesai.theweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sahildesai.theweatherapp.data.utils.LocationService
import dev.sahildesai.theweatherapp.domain.usecase.GetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.domain.usecase.GetWeatherUseCase
import dev.sahildesai.theweatherapp.domain.usecase.IGetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.domain.usecase.IGetWeatherUseCase
import dev.sahildesai.theweatherapp.repository.IWeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetCurrentLocationUseCase(
        locationService: LocationService
    ): IGetCurrentLocationUseCase = GetCurrentLocationUseCase(locationService)

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: IWeatherRepository): IGetWeatherUseCase =
        GetWeatherUseCase(repository)
}