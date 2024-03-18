package android.azadev.weatherry.domain.repository

import android.azadev.weatherry.domain.model.WeatherData

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

interface WeatherRepository {

    suspend fun getWeatherDataByLocation(location: String): WeatherData
}