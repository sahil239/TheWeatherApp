package dev.sahildesai.theweatherapp.domain

import android.location.Location

interface IGetCurrentLocationUseCase {
   suspend fun getCurrentLocation(): Location?
}