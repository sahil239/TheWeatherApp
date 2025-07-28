package dev.sahildesai.theweatherapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import dev.sahildesai.theweatherapp.ui.PermissionState
import dev.sahildesai.theweatherapp.ui.RequestLocationPermissionIfNeeded

@Composable
fun WeatherScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val permissionState by viewModel.permissionState.collectAsState()
    val location by viewModel.location.collectAsState()

    RequestLocationPermissionIfNeeded(
        permissionState = permissionState,
        onPermissionResult = viewModel::onPermissionResult
    )

    Column(Modifier.padding(16.dp)) {
        when {
            location != null -> {
                Text("Lat: ${location!!.latitude}")
                Text("Lon: ${location!!.longitude}")
            }
            permissionState == PermissionState.DENIED -> {
                Text("Permission denied. Please enable location.")
            }
            else -> {
                Text("Requesting location...")
            }
        }
    }
}