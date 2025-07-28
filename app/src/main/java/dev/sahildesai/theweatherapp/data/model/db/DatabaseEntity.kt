package dev.sahildesai.theweatherapp.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sahildesai.theweatherapp.data.model.api.WeatherDescription

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val dt: Long, // Use timestamp as primary key
    val date: String,
    val sunrise: Long,
    val sunset: Long,
    val feelsLike: Double?,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int = 0,
    val windSpeed: Double,
    val windDeg: Int,
    val minTemp: Double?,     //current:: null
    val maxTemp: Double?,     //current:: null
    val temp: Double?,        //daily:: null
    val weatherDescription: List<WeatherDescription>,
    val isCurrent: Boolean    // To differentiate current vs. daily
)