package dev.sahildesai.theweatherapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import dev.sahildesai.theweatherapp.BuildConfig
import dev.sahildesai.theweatherapp.data.model.api.WeatherResponse
import retrofit2.Response

interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}