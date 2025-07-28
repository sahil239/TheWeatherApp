package dev.sahildesai.theweatherapp.ui.home

import android.location.Location
import dev.sahildesai.theweatherapp.domain.model.WeatherResult
import dev.sahildesai.theweatherapp.domain.usecase.IGetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.domain.usecase.IGetWeatherUseCase
import dev.sahildesai.theweatherapp.ui.PermissionState
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel

    private val mockGetLocation = mockk<IGetCurrentLocationUseCase>()
    private val mockGetWeather = mockk<IGetWeatherUseCase>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockGetLocation, mockGetWeather)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onPermissionResult granted should fetch location and weather`() = runTest {
        val location = mockk<Location>()
        every { location.latitude } returns 10.0
        every { location.longitude } returns 20.0
        coEvery { mockGetLocation.getCurrentLocation() } returns location
        coEvery { mockGetWeather.getWeather(10.0, 20.0) } returns WeatherResult.Success(emptyList())

        viewModel.onPermissionResult(true)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.permissionState.value shouldBe PermissionState.GRANTED
        viewModel.weatherState.value shouldBe WeatherUiState.Success(emptyList(), isCached = false)
    }

    @Test
    fun `onPermissionResult denied should not fetch data`() {
        viewModel.onPermissionResult(false)
        viewModel.permissionState.value shouldBe PermissionState.DENIED
        viewModel.weatherState.value shouldBe WeatherUiState.PermissionDenied
    }

    @Test
    fun `onPermissionResult granted but location is null`() = runTest {
        coEvery { mockGetLocation.getCurrentLocation() } returns null

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        viewModel.weatherState.value shouldBe WeatherUiState.Loading // no weather fetch
    }

    @Test
    fun `weather fetch returns cached data`() = runTest {
        val location = mockk<Location> {
            every { latitude } returns 10.0
            every { longitude } returns 20.0
        }
        coEvery { mockGetLocation.getCurrentLocation() } returns location
        coEvery { mockGetWeather.getWeather(10.0, 20.0) } returns WeatherResult.Cached(emptyList())

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        viewModel.weatherState.value shouldBe WeatherUiState.Success(emptyList(), isCached = true)
    }

    @Test
    fun `weather fetch returns error`() = runTest {
        val location = mockk<Location> {
            every { latitude } returns 10.0
            every { longitude } returns 20.0
        }
        coEvery { mockGetLocation.getCurrentLocation() } returns location
        coEvery { mockGetWeather.getWeather(10.0, 20.0) } returns WeatherResult.Error("Network Error")

        viewModel.onPermissionResult(true)
        advanceUntilIdle()

        viewModel.weatherState.value shouldBe WeatherUiState.Error("Network Error")
    }
}
