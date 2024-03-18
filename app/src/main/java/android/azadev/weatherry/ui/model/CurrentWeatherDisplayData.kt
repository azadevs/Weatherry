package android.azadev.weatherry.ui.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class CurrentWeatherDisplayData(
    val cityName: String,
    val date: String,
    val currentTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val icon: String,
    val condition: String,
    val sunrise: String,
    val sunset: String,
    val currentWeatherDetails: CurrentWeatherDetails
)
