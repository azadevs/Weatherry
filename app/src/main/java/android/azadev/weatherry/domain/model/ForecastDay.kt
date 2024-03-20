package android.azadev.weatherry.domain.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class ForecastDay(
    val icon: String,
    val maxTemp: Int,
    val minTemp: Int,
    val dayOfWeek: String,
    val hour: List<Hour>,
    val sunrise: String,
    val sunset: String
)

data class Hour(
    val time: String,
    val icon: String
)