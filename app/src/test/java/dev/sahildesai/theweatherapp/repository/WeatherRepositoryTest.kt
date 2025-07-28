package dev.sahildesai.theweatherapp.repository

import dev.sahildesai.theweatherapp.data.api.WeatherApiService
import dev.sahildesai.theweatherapp.data.db.WeatherDao
import dev.sahildesai.theweatherapp.data.model.toDomain
import dev.sahildesai.theweatherapp.data.model.toEntity
import dev.sahildesai.theweatherapp.domain.model.WeatherResult
import dev.sahildesai.theweatherapp.mockCurrentWeatherDto
import dev.sahildesai.theweatherapp.mockDailyForecastDto
import dev.sahildesai.theweatherapp.mockWeatherResponse
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException


class WeatherRepositoryTest {

    private val apiService: WeatherApiService = mockk()
    private val weatherDao: WeatherDao = mockk()
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        repository = WeatherRepository(apiService, weatherDao)
    }

    @Test
    fun `getWeather returns success and stores data`() = runTest {
        val response = mockWeatherResponse()

        coEvery { apiService.getWeather(any(), any()) } returns Response.success(response)
        coEvery { weatherDao.insertAll(any()) } just Runs

        val result = repository.getWeather(10.0, 20.0)

        result shouldBe
                WeatherResult.Success(listOf(mockCurrentWeatherDto().toEntity().toDomain(), mockDailyForecastDto().toEntity().toDomain()))
        coVerify { weatherDao.insertAll(match { it.size == 2 }) }
    }

    @Test
    fun `getWeather returns cached data on API failure`() = runTest {
        coEvery { apiService.getWeather(any(), any()) } throws IOException("Timeout")
        coEvery { weatherDao.getAllDailyWeather() } returns listOf(mockDailyForecastDto().toEntity())

        val result = repository.getWeather(10.0, 20.0)

        result shouldBe WeatherResult.Cached(listOf(mockDailyForecastDto().toEntity().toDomain()))
    }

    @Test
    fun `getWeather returns error if API and cache both fail`() = runTest {
        coEvery { apiService.getWeather(any(), any()) } throws IOException("Server error")
        coEvery { weatherDao.getAllDailyWeather() } returns emptyList()

        val result = repository.getWeather(10.0, 20.0)

        result shouldBe WeatherResult.Error("Server error")
    }
}