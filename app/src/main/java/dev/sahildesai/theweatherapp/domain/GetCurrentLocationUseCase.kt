package dev.sahildesai.theweatherapp.domain

import android.location.Location
import dev.sahildesai.theweatherapp.data.LocationService

class GetCurrentLocationUseCase(
    private val locationService: LocationService
): IGetCurrentLocationUseCase {
    override suspend fun getCurrentLocation(): Location? = locationService.getCurrentLocation()
}