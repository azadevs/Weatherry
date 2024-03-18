package android.azadev.weatherry.data.repository

import android.azadev.weatherry.data.mapper.fromNetwork
import android.azadev.weatherry.data.remote.api.WeatherApi
import android.azadev.weatherry.domain.model.WeatherData
import android.azadev.weatherry.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

class ProdWeatherRepository(
    private val remoteSource: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherDataByLocation(location: String): WeatherData =
        withContext(Dispatchers.IO) {
            remoteSource.getCurrentLocation(a = location).fromNetwork()
        }
}