package android.azadev.weatherry.data.remote.response


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: WeatherCurrent,
    @SerializedName("forecast")
    val forecast: WeatherForecast,
    @SerializedName("location")
    val location: WeatherLocation
)