package dev.sahildesai.theweatherapp.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sahildesai.theweatherapp.domain.IGetCurrentLocationUseCase
import dev.sahildesai.theweatherapp.ui.PermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val getCurrentLocationUseCase: IGetCurrentLocationUseCase
): ViewModel() {

    private val _permissionState = MutableStateFlow(PermissionState.UNKNOWN)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    fun onPermissionResult(granted: Boolean) {
        _permissionState.value = if (granted) PermissionState.GRANTED else PermissionState.DENIED
        if (granted) fetchLocation()
    }

    private fun fetchLocation() {
        viewModelScope.launch {
            _location.value = getCurrentLocationUseCase.getCurrentLocation()
        }
    }

}
