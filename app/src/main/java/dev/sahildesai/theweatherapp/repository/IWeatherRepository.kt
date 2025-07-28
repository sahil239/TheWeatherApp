package dev.sahildesai.theweatherapp.repository

import dev.sahildesai.theweatherapp.domain.model.WeatherResult

interface IWeatherRepository {
    suspend fun getWeather(lat: Double, lng: Double): WeatherResult
}