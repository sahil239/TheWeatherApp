package dev.sahildesai.theweatherapp.data.model

import dev.sahildesai.theweatherapp.data.model.api.CurrentWeather
import dev.sahildesai.theweatherapp.data.model.api.DailyForecast
import dev.sahildesai.theweatherapp.data.model.db.WeatherEntity
import dev.sahildesai.theweatherapp.domain.model.Weather
import dev.sahildesai.theweatherapp.ui.model.WeatherUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun CurrentWeather.toEntity(): WeatherEntity {
    return WeatherEntity(
        dt = dt,
        date = Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("EEE, MMM d")),
        temp = temp,
        minTemp = null,
        maxTemp = null,
        weatherDescription = weather,
        sunrise = sunrise,
        sunset = sunset,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
        isCurrent = true
    )
}

fun DailyForecast.toEntity(): WeatherEntity {
    return WeatherEntity(
        dt = dt,
        date = Instant.ofEpochSecond(dt)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("EEE, MMM d")),
        temp = null,
        minTemp = temp.min,
        maxTemp = temp.max,
        weatherDescription = weather,
        sunrise = sunrise,
        sunset = sunset,
        feelsLike = feelsLike.day,
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        windSpeed = windSpeed,
        windDeg = windDeg,
        isCurrent = false
    )
}

fun WeatherEntity.toUiModel(): WeatherUiModel {
    val dateFormatted = Instant.ofEpochSecond(dt)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("EEE, MMM d"))

    val sunriseFormatted = Instant.ofEpochSecond(sunrise)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("hh:mm a"))

    val sunsetFormatted = Instant.ofEpochSecond(sunset)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("hh:mm a"))

    return WeatherUiModel(
        date = dateFormatted,
        temperature = temp?.let { "$it°C" } ?: "N/A" ,
        minTemperature = minTemp?.let { "$it°C" } ?: "N/A",
        maxTemperature = maxTemp?.let { "$it°C" } ?: "N/A",
        iconDescriptionPairs = weatherDescription.map { description ->
            val iconUrl = "https://openweathermap.org/img/wn/${description.icon}@2x.png"
            val desc = description.description.replaceFirstChar(Char::uppercase)
            iconUrl to desc
        },
        isCurrent = isCurrent,
        sunrise = sunriseFormatted,
        sunset = sunsetFormatted,
        feelsLike = "$feelsLike°C",
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
    )
}

fun Weather.toUiModel(): WeatherUiModel {
    val dateFormatted = Instant.ofEpochSecond(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("EEE, MMM d"))

    val sunriseFormatted = Instant.ofEpochSecond(sunrise)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("hh:mm a"))

    val sunsetFormatted = Instant.ofEpochSecond(sunset)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("hh:mm a"))

    return WeatherUiModel(
        date = dateFormatted,
        temperature = temperature?.let { "$it°C" } ?: "N/A" ,
        minTemperature = minTemp?.let { "$it°C" } ?: "N/A",
        maxTemperature = maxTemp?.let { "$it°C" } ?: "N/A",
        iconDescriptionPairs = weatherDescriptions.map { description ->
            val iconUrl = "https://openweathermap.org/img/wn/${description.icon}@2x.png"
            val desc = description.description.replaceFirstChar(Char::uppercase)
            iconUrl to desc
        },
        isCurrent = isCurrent,
        sunrise = sunriseFormatted,
        sunset = sunsetFormatted,
        feelsLike = "$feelsLike°C",
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
    )
}


fun WeatherEntity.toDomain(): Weather = Weather(
    timestamp = dt,
    date = date,
    sunrise = sunrise,
    sunset = sunset,
    feelsLike = feelsLike,
    pressure = pressure,
    humidity = humidity,
    dewPoint = dewPoint,
    uvi = uvi,
    clouds = clouds,
    visibility = visibility,
    windSpeed = windSpeed,
    windDeg = windDeg,
    minTemp = minTemp,
    maxTemp = maxTemp,
    temperature = temp,
    weatherDescriptions = weatherDescription,
    isCurrent = isCurrent
)

fun Weather.toEntity(): WeatherEntity = WeatherEntity(
    dt = timestamp,
    date = date,
    sunrise = sunrise,
    sunset = sunset,
    feelsLike = feelsLike,
    pressure = pressure,
    humidity = humidity,
    dewPoint = dewPoint,
    uvi = uvi,
    clouds = clouds,
    visibility = visibility,
    windSpeed = windSpeed,
    windDeg = windDeg,
    minTemp = minTemp,
    maxTemp = maxTemp,
    temp = temperature,
    weatherDescription = weatherDescriptions,
    isCurrent = isCurrent
)
