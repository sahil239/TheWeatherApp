package dev.sahildesai.theweatherapp

import dev.sahildesai.theweatherapp.data.model.api.CurrentWeather
import dev.sahildesai.theweatherapp.data.model.api.DailyForecast
import dev.sahildesai.theweatherapp.data.model.api.FeelsLike
import dev.sahildesai.theweatherapp.data.model.api.Temperature
import dev.sahildesai.theweatherapp.data.model.api.WeatherDescription
import dev.sahildesai.theweatherapp.data.model.api.WeatherResponse

fun mockCurrentWeatherDto() = CurrentWeather(
    dt = 1620000000,
    temp = 22.0,
    feelsLike = 21.0,
    pressure = 1013,
    humidity = 60,
    windSpeed = 5.0,
    windDeg = 180,
    sunrise = 12131,
    sunset = 1234456,
    dewPoint = 122.0,
    uvi = 1.0,
    clouds = 1,
    visibility = 133,
    weather = emptyList<WeatherDescription>()
)

fun mockDailyForecastDto() = DailyForecast(
    dt = 1620000000,
    temp = Temperature(day = 0.0, min = 0.0, max = 0.0),
    feelsLike = FeelsLike(day = 0.0, night = 0.0, eve = 0.0, morn = 0.0),
    pressure = 1013,
    humidity = 60,
    windSpeed = 5.0,
    windDeg = 180,
    sunrise = 12131,
    sunset = 1234456,
    dewPoint = 122.0,
    uvi = 1.0,
    clouds = 1,
    weather = emptyList<WeatherDescription>(),
    summary = ""
)

fun mockWeatherResponse() =  WeatherResponse(
    current = mockCurrentWeatherDto(),
    daily = listOf(mockDailyForecastDto(),),
    lat = 0.0,
    lon = 0.0,
    timezone = "",
    timezoneOffset = 2
)