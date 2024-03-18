package android.azadev.weatherry.data.remote.response


import com.google.gson.annotations.SerializedName

data class WeatherCondition(
    @SerializedName("code")
    val code: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)