package dev.sahildesai.theweatherapp.domain.model

sealed class WeatherResult {
    data class Success(val data: List<Weather>) : WeatherResult()
    data class Cached(val data: List<Weather>) : WeatherResult()
    data class Error(val message: String) : WeatherResult()
}
