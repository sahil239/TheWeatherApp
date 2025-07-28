package dev.sahildesai.theweatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sahildesai.theweatherapp.data.model.db.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}