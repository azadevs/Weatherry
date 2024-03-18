package android.azadev.weatherry.ui.home.viewmodel

import android.azadev.weatherry.domain.repository.WeatherRepository
import android.azadev.weatherry.ui.model.CurrentWeatherDetails
import android.azadev.weatherry.ui.model.CurrentWeatherDisplayData
import android.azadev.weatherry.ui.model.ForecastDayDisplayData
import android.azadev.weatherry.ui.model.ForecastDisplayData
import android.azadev.weatherry.ui.model.ForecastHour
import android.azadev.weatherry.utils.Constants
import android.azadev.weatherry.utils.Resource
import android.azadev.weatherry.utils.parseLongToDate
import android.azadev.weatherry.utils.parseLongToDateTime
import android.azadev.weatherry.utils.parseLongToDayOfWeek
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

class HomeViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _currentState =
        MutableStateFlow<Resource<CurrentWeatherDisplayData>>(Resource.Loading)
    val currentState = _currentState.asStateFlow()

    private val _forecastState = MutableStateFlow<Resource<ForecastDisplayData>>(Resource.Loading)
    val forecastState = _forecastState.asStateFlow()

    init {
        getForecastWeatherDataByLocation()
        getCurrentWeatherDataByLocation()
    }

    private fun getCurrentWeatherDataByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data =
                    repository.getWeatherDataByLocation("${41.377491},${64.585262}")
                _currentState.value =
                    Resource.Success(
                        CurrentWeatherDisplayData(
                            cityName = data.location.cityName,
                            date = parseLongToDate(data.forecast.forecastday.first().dateEpoch),
                            currentTemp = data.current.tempC,
                            minTemp = data.forecast.forecastday.first().day.minTemp,
                            maxTemp = data.forecast.forecastday.first().day.maxTemp,
                            icon = Constants.fromWMO(data.current.condition.code),
                            condition = data.current.condition.text,
                            sunrise = data.forecast.forecastday.first().astro.sunrise,
                            sunset = data.forecast.forecastday.first().astro.sunset,
                            currentWeatherDetails = CurrentWeatherDetails(
                                wind = data.current.windKph,
                                pressure = data.current.pressureIn,
                                precipitation = data.current.precipIn,
                                humidity = data.current.humidity,
                                vis = data.current.vis,
                                uv = data.current.uv
                            )
                        )
                    )

            } catch (e: Exception) {
                _currentState.value = Resource.Error(e.localizedMessage ?: "error occurred")
            }
        }
    }

    private fun getForecastWeatherDataByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getWeatherDataByLocation("${41.377491},${64.585262}")
                _forecastState.value = Resource.Success(
                    ForecastDisplayData(data.forecast.forecastday.map {
                        ForecastDayDisplayData(
                            dayOfWeek = parseLongToDayOfWeek(it.dateEpoch),
                            maxTemp = it.day.maxTemp,
                            minTemp = it.day.minTemp,
                            icon = it.day.icon,
                            hour = it.hour.map { hour ->
                                ForecastHour(
                                    icon = hour.icon,
                                    time = parseLongToDateTime(hour.timeEpoch)
                                )
                            }

                        )
                    })
                )
            } catch (e: Exception) {
                _forecastState.value = Resource.Error(e.localizedMessage ?: "error occurred")
            }
        }
    }


}