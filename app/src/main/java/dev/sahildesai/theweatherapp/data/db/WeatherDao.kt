package dev.sahildesai.theweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sahildesai.theweatherapp.data.model.db.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY dt ASC")
    suspend fun getAllDailyWeather(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherList: List<WeatherEntity>)
}