package android.azadev.weatherry.domain.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

data class WeatherData(
    val current: Current,
    val forecast: List<ForecastDay>,
)
