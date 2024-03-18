package android.azadev.weatherry.domain.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class Current(
    val humidity: Int,
    val precipIn: Double,
    val pressureIn: Double,
    val tempC: Int,
    val uv: Double,
    val windKph: Double,
    val vis: Double,
    val text: String,
    val code: Int,
    val cityName: String
)