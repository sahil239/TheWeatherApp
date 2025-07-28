package dev.sahildesai.theweatherapp.domain.usecase

import android.location.Location
import dev.sahildesai.theweatherapp.data.utils.LocationService

class GetCurrentLocationUseCase(
    private val locationService: LocationService
): IGetCurrentLocationUseCase {
    override suspend fun getCurrentLocation(): Location? = locationService.getCurrentLocation()
}