package dev.sahildesai.theweatherapp.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.sahildesai.theweatherapp.ui.PermissionState
import dev.sahildesai.theweatherapp.ui.RequestLocationPermissionIfNeeded
import dev.sahildesai.theweatherapp.ui.model.WeatherUiModel
import dev.sahildesai.theweatherapp.ui.theme.Background
import dev.sahildesai.theweatherapp.ui.theme.CardBackground
import dev.sahildesai.theweatherapp.ui.widgets.LoadImageFromUrl
import dev.sahildesai.theweatherapp.ui.widgets.LoadingData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMainScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val permissionState by viewModel.permissionState.collectAsState()
    val weather by viewModel.weatherState.collectAsState()

    var selectedDay by remember { mutableStateOf<WeatherUiModel?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    fun setSelectedDay(selectedWeatherUiModel: WeatherUiModel?) {
        selectedDay = selectedWeatherUiModel
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val isGranted = ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                viewModel.onPermissionResult(isGranted)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    RequestLocationPermissionIfNeeded(
        permissionState = permissionState,
        onPermissionResult = viewModel::onPermissionResult
    )

    when (weather) {
        is WeatherUiState.Loading -> LoadingData()

        is WeatherUiState.PermissionDenied -> OnPermissionDenied(permissionState)

        is WeatherUiState.Success -> {
            val weatherData = (weather as WeatherUiState.Success).data
            WeatherScreen(
                weatherList = weatherData,
                isCached = (weather as WeatherUiState.Success).isCached,
                onClick = { setSelectedDay(it) }
            )
        }

        is WeatherUiState.Error -> {
            val message = (weather as WeatherUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $message", color = Color.Red)
            }
        }
    }

    if(selectedDay != null){
        ModalBottomSheet(
            onDismissRequest = { setSelectedDay(null) },
            sheetState = rememberModalBottomSheetState(),
            dragHandle = { BottomSheetDefaults.DragHandle() },
            tonalElevation = 8.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            selectedDay?.let {
                BottomSheetDetails(it)
            }
        }
    }
}

@Composable
private fun OnPermissionDenied(permissionState: PermissionState){
    if (permissionState != PermissionState.GRANTED) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Location permission is required to show weather info.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }) {
                Text("Open Settings")
            }
        }
    }
}
@Composable
private fun WeatherScreen(isCached: Boolean = false,weatherList: List<WeatherUiModel>, onClick: (WeatherUiModel?)-> Unit) {

    Column(modifier = Modifier.fillMaxSize().background(Background).padding(16.dp)) {
        if(isCached) Text("Offline data.", modifier = Modifier.padding(10.dp).fillMaxWidth(), textAlign = TextAlign.Center)

        if (weatherList.isNotEmpty()) {
            LazyColumn {
                val current = weatherList.first()
                item {  CurrentWeatherCard(current) }

                item { Text("Forecast", style = MaterialTheme.typography.titleLarge) }
                item { Text("Tap to view details", style = MaterialTheme.typography.bodyMedium) }

                val forecast = weatherList.filter { !it.isCurrent }

                items(forecast.size) { day ->
                    DailyWeatherCard(forecast[day], onClick)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text(
                text = "No weather data available",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CurrentWeatherCard(current: WeatherUiModel){
    Text("Today", style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.height(8.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {

        LoadImageFromUrl(
            title = "current",
            imageUrl = current.iconDescriptionPairs.firstOrNull()?.first.orEmpty()
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = "Temperature: ${current.temperature}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Condition: ${current.iconDescriptionPairs.firstOrNull()?.second.orEmpty()}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    SecondaryInfo(current)

    Spacer(Modifier.height(24.dp))
}
@Composable
private fun DailyWeatherCard(day: WeatherUiModel, onClick: (WeatherUiModel?) -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground.copy(alpha = 0.6f)
        ),
        onClick = {onClick(day)}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = day.date, style = MaterialTheme.typography.titleMedium, color = Color.White)
            RowForTwoItems(false, "Condition: ${day.iconDescriptionPairs.firstOrNull()?.second.orEmpty()}")
        }
    }
}

@Composable
private fun BottomSheetDetails(weatherUiModel: WeatherUiModel){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = weatherUiModel.date,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        item {
            RowForTwoItems(true, "Condition: ${weatherUiModel.iconDescriptionPairs.firstOrNull()?.second.orEmpty()}")
            RowForTwoItems(true, "Min Temp: ${weatherUiModel.minTemperature}", "Max Temp: ${weatherUiModel.maxTemperature}")
            RowForTwoItems(true, "Temperature: ${weatherUiModel.temperature}", "Feels Like: ${weatherUiModel.feelsLike}")
            RowForTwoItems(true, "Humidity: ${weatherUiModel.humidity}%", "Clouds: ${weatherUiModel.clouds}%")
            RowForTwoItems(true, "Pressure: ${weatherUiModel.pressure} hPa", "UV Index: ${weatherUiModel.uvi}")
            RowForTwoItems(true, "Sunrise: ${weatherUiModel.sunrise}", "Sunset: ${weatherUiModel.sunset}")
            RowForTwoItems(true, "Wind: ${weatherUiModel.windSpeed} m/s", "Direction: ${weatherUiModel.windDeg}°")
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(weatherUiModel.iconDescriptionPairs.size) { index ->
                val pair = weatherUiModel.iconDescriptionPairs[index]
                Column(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadImageFromUrl(
                        imageUrl = pair.first,
                        title = pair.second,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = pair.second,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        }
    }
}

@Composable
private fun SecondaryInfo(weatherUiModel: WeatherUiModel) {
    RowForTwoItems(weatherUiModel.isCurrent,"Sunrise: ${(weatherUiModel.sunrise)}", "Sunset: ${(weatherUiModel.sunset)}")
    RowForTwoItems(weatherUiModel.isCurrent,"Feels like: ${weatherUiModel.feelsLike}","Humidity: ${weatherUiModel.humidity}%")
    RowForTwoItems(weatherUiModel.isCurrent,"Pressure: ${weatherUiModel.pressure} hPa","UV Index: ${weatherUiModel.uvi}")
    RowForTwoItems(weatherUiModel.isCurrent,"Clouds: ${weatherUiModel.clouds}%", "Wind: ${weatherUiModel.windSpeed} m/s at ${weatherUiModel.windDeg}°")
}

@Composable
private fun RowForTwoItems(isCurrent: Boolean, leftText: String, rightText: String? = null){
    var textColor = if(isCurrent) Color.Black else Color.White
    Spacer(Modifier.height(8.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(leftText , color = textColor, style = MaterialTheme.typography.bodyLarge)
        rightText?.let { Text(text = rightText, color = textColor, style = MaterialTheme.typography.bodyLarge) }
    }
}