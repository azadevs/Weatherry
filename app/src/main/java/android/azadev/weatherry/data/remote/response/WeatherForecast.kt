package android.azadev.weatherry.data.remote.response


import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("forecastday")
    val forecastday: List<WeatherForecastday>
)