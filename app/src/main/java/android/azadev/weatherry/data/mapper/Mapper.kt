package android.azadev.weatherry.data.mapper

import android.azadev.weatherry.data.remote.response.WeatherResponse
import android.azadev.weatherry.domain.model.Astro
import android.azadev.weatherry.domain.model.Condition
import android.azadev.weatherry.domain.model.Current
import android.azadev.weatherry.domain.model.Day
import android.azadev.weatherry.domain.model.Forecast
import android.azadev.weatherry.domain.model.ForecastDay
import android.azadev.weatherry.domain.model.Hour
import android.azadev.weatherry.domain.model.Location
import android.azadev.weatherry.domain.model.WeatherData

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

fun WeatherResponse.fromNetwork(): WeatherData = WeatherData(
    current = Current(
        condition = Condition(
            code = current.condition.code,
            icon = current.condition.icon,
            text = current.condition.text
        ),
        humidity = current.humidity,
        precipIn = current.precipIn,
        pressureIn = current.pressureIn,
        tempC = current.tempC.toInt(),
        uv = current.uv,
        windKph = current.windKph,
        vis = current.visKm
    ),
    forecast = Forecast(
        forecastday = forecast.forecastday.map {
            ForecastDay(
                astro = Astro(sunrise = it.astro.sunrise, sunset = it.astro.sunset),
                dateEpoch = it.dateEpoch,
                day = Day(
                    icon = it.day.condition.icon,
                    maxTemp = it.day.maxtempC.toInt(),
                    minTemp = it.day.mintempC.toInt()
                ),
                hour = it.hour.map { hour ->
                    Hour(
                        timeEpoch = hour.timeEpoch, icon = hour.condition.icon
                    )
                }
            )
        }
    ),
    location = Location(
        cityName = location.name
    )
)