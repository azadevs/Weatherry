package android.azadev.weatherry.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/18/2024
 */

@Parcelize
data class CurrentWeatherDetails(
    val wind: Double,
    val pressure: Double,
    val precipitation: Double,
    val humidity: Int,
    val vis: Double,
    val uv: Double
) : Parcelable
