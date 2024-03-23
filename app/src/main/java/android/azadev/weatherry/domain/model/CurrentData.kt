package android.azadev.weatherry.domain.model

import androidx.annotation.DrawableRes

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class CurrentData(
    val cityName: String,
    val country: String,
    val date: String,
    val currentTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    @DrawableRes val icon: Int,
    val condition: String,
    val sunrise: String,
    val sunset: String,
    val details: CurrentDetails
)