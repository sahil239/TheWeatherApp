package dev.sahildesai.theweatherapp.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sahildesai.theweatherapp.data.model.toEntity
import dev.sahildesai.theweatherapp.data.model.toUiModel
import dev.sahildesai.theweatherapp.domain.model.WeatherResult
import dev.sahildesai.theweatherapp.domain.usecase.IGetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.domain.usecase.IGetWeatherUseCase
import dev.sahildesai.theweatherapp.ui.PermissionState
import dev.sahildesai.theweatherapp.ui.model.WeatherUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data object PermissionDenied : WeatherUiState()
    data class Success(val data: List<WeatherUiModel>, val isCached: Boolean) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentLocationUseCase: IGetCurrentLocationUseCase,
    private val getWeatherUseCase: IGetWeatherUseCase
) : ViewModel() {

    private val _permissionState = MutableStateFlow(PermissionState.UNKNOWN)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    private val _location = MutableStateFlow<Location?>(null)

    fun onPermissionResult(granted: Boolean) {
        _permissionState.value = if (granted) PermissionState.GRANTED else PermissionState.DENIED
        if (granted) {
            fetchLocation()
        }else {
            _weatherState.value = WeatherUiState.PermissionDenied
        }
    }

    private fun fetchLocation() {
        viewModelScope.launch {
            _location.value = getCurrentLocationUseCase.getCurrentLocation()
            fetchWeather()
        }
    }

    private fun fetchWeather() {
        _location.value?.let { location ->
            viewModelScope.launch {
                    _weatherState.value = WeatherUiState.Loading
                    when (val result = getWeatherUseCase.getWeather(location.latitude, location.longitude)) {
                        is WeatherResult.Success -> _weatherState.value = WeatherUiState.Success(result.data.map { it.toUiModel() }, false)
                        is WeatherResult.Cached -> _weatherState.value = WeatherUiState.Success(result.data.map { it.toUiModel() }, true)
                        is WeatherResult.Error -> _weatherState.value = WeatherUiState.Error(result.message)
                    }
                }
        }
    }

}
