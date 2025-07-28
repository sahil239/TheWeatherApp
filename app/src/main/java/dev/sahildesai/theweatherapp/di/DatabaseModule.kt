package dev.sahildesai.theweatherapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sahildesai.theweatherapp.data.db.WeatherDao
import dev.sahildesai.theweatherapp.data.db.WeatherDatabase
import dev.sahildesai.theweatherapp.data.db.WeatherTypeConverters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            name = "weather.db"
        ).addTypeConverter(WeatherTypeConverters()).build()

    @Singleton
    @Provides
    fun providesPodcastDao(podcastDatabase: WeatherDatabase): WeatherDao = podcastDatabase.weatherDao()

}