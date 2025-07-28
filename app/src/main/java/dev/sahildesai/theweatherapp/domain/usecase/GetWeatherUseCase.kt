package dev.sahildesai.theweatherapp.domain.usecase

import dev.sahildesai.theweatherapp.domain.model.WeatherResult
import dev.sahildesai.theweatherapp.repository.IWeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: IWeatherRepository
): IGetWeatherUseCase {
    override suspend fun getWeather(lat: Double, lng: Double): WeatherResult {
        return repository.getWeather(lat, lng)
    }
}