package dev.sahildesai.theweatherapp.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import dev.sahildesai.theweatherapp.data.model.api.WeatherDescription
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class WeatherTypeConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromWeatherList(value: List<WeatherDescription>): String {
        return json.encodeToString(ListSerializer(WeatherDescription.serializer()), value)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<WeatherDescription> {
        return json.decodeFromString(ListSerializer(WeatherDescription.serializer()), value)
    }
}