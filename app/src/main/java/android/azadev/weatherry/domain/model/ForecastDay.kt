package android.azadev.weatherry.domain.model

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

data class ForecastDay(
    val astro: Astro,
    val dateEpoch: Long,
    val day: Day,
    val hour: List<Hour>
)