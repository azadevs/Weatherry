package android.azadev.weatherry.ui.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class ForecastDayDisplayData(
    val dayOfWeek: String,
    val maxTemp: Int,
    val minTemp: Int,
    val icon: String,
    val hour: List<ForecastHour>
)

data class ForecastHour(
    val icon: String,
    val time: String
)
