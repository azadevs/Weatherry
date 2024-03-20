package android.azadev.weatherry.data.remote.api

import android.azadev.weatherry.BuildConfig
import android.azadev.weatherry.data.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getCurrentWeatherLocation(
        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") a: String,
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getForecastDaysWeatherByLocation(
        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") a: String,
        @Query("days") day: Int = 7
    ): WeatherResponse

}