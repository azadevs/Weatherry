package android.azadev.weatherry.data.remote.response


import com.google.gson.annotations.SerializedName

data class WeatherForecastday(
    @SerializedName("astro")
    val astro: WeatherAstro,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Long,
    @SerializedName("day")
    val day: WeatherDay,
    @SerializedName("hour")
    val hour: List<WeatherHour>
)