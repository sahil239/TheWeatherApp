package dev.sahildesai.theweatherapp.domain.model

import dev.sahildesai.theweatherapp.data.model.api.WeatherDescription

data class Weather(
    val timestamp: Long,
    val date: String,
    val sunrise: Long,
    val sunset: Long,
    val feelsLike: Double?,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val minTemp: Double?,
    val maxTemp: Double?,
    val temperature: Double?,
    val weatherDescriptions: List<WeatherDescription>,
    val isCurrent: Boolean
)
