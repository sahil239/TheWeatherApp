package dev.sahildesai.theweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sahildesai.theweatherapp.data.LocationService
import dev.sahildesai.theweatherapp.domain.GetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.domain.IGetCurrentLocationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetCurrentLocationUseCase(locationService: LocationService): IGetCurrentLocationUseCase = GetCurrentLocationUseCase(locationService)
}