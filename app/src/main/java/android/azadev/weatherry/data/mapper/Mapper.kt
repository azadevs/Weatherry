package android.azadev.weatherry.data.mapper

import android.azadev.weatherry.data.remote.response.WeatherResponse
import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.CurrentDetails
import android.azadev.weatherry.domain.model.ForecastData
import android.azadev.weatherry.domain.model.ForecastDay
import android.azadev.weatherry.domain.model.Hour
import android.azadev.weatherry.utils.Constants
import android.azadev.weatherry.utils.parseLongToDate
import android.azadev.weatherry.utils.parseLongToDateTime
import android.azadev.weatherry.utils.parseLongToDayOfWeek

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

fun WeatherResponse.toCurrentData(): CurrentData = CurrentData(
    cityName = location.name,
    date = parseLongToDate(current.lastUpdatedEpoch),
    details = CurrentDetails(
        wind = current.windKph,
        pressure = current.pressureIn,
        precipitation = current.precipIn,
        humidity = current.humidity,
        vis = current.visKm,
        uv = current.uv,
    ),
    currentTemp = current.tempC.toInt(),
    minTemp = forecast.forecastday.first().day.mintempC.toInt(),
    maxTemp = forecast.forecastday.first().day.maxtempC.toInt(),
    icon = Constants.fromWMO(current.condition.code),
    condition = current.condition.text,
    sunrise = forecast.forecastday.first().astro.sunrise,
    sunset = forecast.forecastday.first().astro.sunset
)

fun WeatherResponse.toForecastData(): ForecastData =
    ForecastData(forecast = forecast.forecastday.map {
        ForecastDay(
            icon = it.day.condition.icon,
            maxTemp = it.day.maxtempC.toInt(),
            minTemp = it.day.mintempC.toInt(),
            dayOfWeek = parseLongToDayOfWeek(it.dateEpoch),
            hour = it.hour.map { hour ->
                Hour(
                    time = parseLongToDateTime(hour.timeEpoch), icon = hour.condition.icon
                )
            },
            sunrise = it.astro.sunrise,
            sunset = it.astro.sunset
        )
    })