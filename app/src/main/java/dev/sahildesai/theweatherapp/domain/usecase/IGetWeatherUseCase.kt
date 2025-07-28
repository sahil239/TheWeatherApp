package dev.sahildesai.theweatherapp.domain.usecase

import dev.sahildesai.theweatherapp.domain.model.WeatherResult

interface IGetWeatherUseCase {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResult
}