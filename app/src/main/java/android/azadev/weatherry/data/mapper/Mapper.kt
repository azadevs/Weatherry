package android.azadev.weatherry.data.mapper

import android.azadev.weatherry.data.remote.response.WeatherResponse
import android.azadev.weatherry.domain.model.Current
import android.azadev.weatherry.domain.model.ForecastDay
import android.azadev.weatherry.domain.model.Hour
import android.azadev.weatherry.domain.model.WeatherData

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

fun WeatherResponse.fromNetwork(): WeatherData = WeatherData(
    current = Current(
        humidity = current.humidity,
        precipIn = current.precipIn,
        pressureIn = current.pressureIn,
        tempC = current.tempC.toInt(),
        uv = current.uv,
        windKph = current.windKph,
        vis = current.visKm,
        text = current.condition.text,
        code = current.condition.code,
        cityName = location.name
    ),
    forecast = forecast.forecastday.map {
        ForecastDay(
            sunrise = it.astro.sunrise,
            sunset = it.astro.sunset,
            dateEpoch = it.dateEpoch,
            icon = it.day.condition.icon,
            maxTemp = it.day.maxtempC.toInt(),
            minTemp = it.day.mintempC.toInt(),
            hour = it.hour.map { hour ->
                Hour(
                    timeEpoch = hour.timeEpoch, icon = hour.condition.icon
                )
            },
        )
    }
)