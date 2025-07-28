package dev.sahildesai.theweatherapp.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    @SerialName("current") val current: CurrentWeather,
    @SerialName("daily") val daily: List<DailyForecast>
)

@Serializable
data class CurrentWeather(
    @SerialName("dt") val dt: Long,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("uvi") val uvi: Double,
    @SerialName("clouds") val clouds: Int,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("temp") val temp: Double,
    @SerialName("weather") val weather: List<WeatherDescription>
)

@Serializable
data class DailyForecast(
    @SerialName("dt") val dt: Long,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long,
    @SerialName("feels_like") val feelsLike: FeelsLike,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("uvi") val uvi: Double,
    @SerialName("clouds") val clouds: Int,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("temp") val temp: Temperature,
    @SerialName("summary") val summary: String,
    @SerialName("weather") val weather: List<WeatherDescription>
)

@Serializable
data class Temperature(
    @SerialName("day") val day: Double,
    @SerialName("min") val min: Double,
    @SerialName("max") val max: Double
)

@Serializable
data class FeelsLike(
    @SerialName("day") val day: Double,
    @SerialName("night") val night: Double,
    @SerialName("eve") val eve: Double,
    @SerialName("morn") val morn: Double
)

@Serializable
data class WeatherDescription(
    @SerialName("id") val id: Int,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)