package dev.sahildesai.theweatherapp.repository

import dev.sahildesai.theweatherapp.data.api.WeatherApiService
import dev.sahildesai.theweatherapp.data.db.WeatherDao
import dev.sahildesai.theweatherapp.data.model.toDomain
import dev.sahildesai.theweatherapp.data.model.toEntity
import dev.sahildesai.theweatherapp.data.utils.ApiResult
import dev.sahildesai.theweatherapp.data.utils.parseAPICall
import dev.sahildesai.theweatherapp.domain.model.WeatherResult
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    private val weatherDao: WeatherDao
) : IWeatherRepository {
    override suspend fun getWeather(lat: Double, lng: Double): WeatherResult {
        val result = parseAPICall { apiService.getWeather(lat, lng) }

        return when (result) {
            is ApiResult.Success -> {

                val current = result.data.current.toEntity()
                val entities = result.data.daily.map { day -> day.toEntity() }

                val allEntities = listOf(current) + entities
                weatherDao.insertAll(allEntities)
                WeatherResult.Success(allEntities.map { it.toDomain() })
            }

            is ApiResult.Failure -> {
                val cached = weatherDao.getAllDailyWeather()
                if (cached.isNotEmpty()) {
                    WeatherResult.Cached(cached.map { it.toDomain() })
                } else {
                    WeatherResult.Error(result.errorMessage)
                }
            }
        }
    }
}