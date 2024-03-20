package android.azadev.weatherry.data.repository

import android.azadev.weatherry.data.mapper.toCurrentData
import android.azadev.weatherry.data.mapper.toForecastData
import android.azadev.weatherry.data.remote.api.WeatherApi
import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.ForecastData
import android.azadev.weatherry.domain.repository.WeatherRepository
import android.azadev.weatherry.domain.util.DataError
import android.azadev.weatherry.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

class ProdWeatherRepository(
    private val remoteSource: WeatherApi
) : WeatherRepository {

    override suspend fun getCurrentWeatherData(location: String): Result<CurrentData, DataError.Network> =
        try {
            Result.Success(withContext(Dispatchers.IO) {
                remoteSource.getCurrentWeatherLocation(a = location).toCurrentData()
            })
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        }

    override suspend fun getForecastWeatherData(location: String): Result<ForecastData, DataError.Network> =
        try {
            Result.Success(withContext(Dispatchers.IO) {
                remoteSource.getForecastDaysWeatherByLocation(a = location).toForecastData()
            })
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        }
}