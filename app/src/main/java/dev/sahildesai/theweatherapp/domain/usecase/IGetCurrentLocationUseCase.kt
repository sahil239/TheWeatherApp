package dev.sahildesai.theweatherapp.domain.usecase

import android.location.Location

interface IGetCurrentLocationUseCase {
   suspend fun getCurrentLocation(): Location?
}