package android.azadev.weatherry.domain.repository

import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.ForecastData
import android.azadev.weatherry.domain.util.DataError
import android.azadev.weatherry.domain.util.Result

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

interface WeatherRepository {

    suspend fun getCurrentWeatherData(location: String): Result<CurrentData, DataError.Network>

    suspend fun getForecastWeatherData(location: String): Result<ForecastData, DataError.Network>
}