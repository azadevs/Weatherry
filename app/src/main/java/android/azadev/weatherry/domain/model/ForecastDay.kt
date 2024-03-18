package android.azadev.weatherry.domain.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class ForecastDay(
    val icon: String,
    val maxTemp: Int,
    val minTemp: Int,
    val dateEpoch: Long,
    val hour: List<Hour>,
    val sunrise: String,
    val sunset: String
)