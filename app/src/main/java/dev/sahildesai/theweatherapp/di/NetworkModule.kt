package dev.sahildesai.theweatherapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sahildesai.theweatherapp.BuildConfig
import dev.sahildesai.theweatherapp.data.api.WeatherApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.BASE_URL
    val contentType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true // very important
    }
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        // Interceptor to add custom headers to each request.
        val headerInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(requestWithHeaders)
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @Singleton
    @Provides
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
