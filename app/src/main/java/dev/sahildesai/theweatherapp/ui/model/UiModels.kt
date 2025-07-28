package dev.sahildesai.theweatherapp.ui.model

data class WeatherUiModel(
    val date: String,
    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val sunrise: String,
    val sunset: String,
    val feelsLike: String,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val iconDescriptionPairs: List<Pair<String, String>>,
    val isCurrent: Boolean
)