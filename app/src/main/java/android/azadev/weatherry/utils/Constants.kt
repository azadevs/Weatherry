package android.azadev.weatherry.utils

import android.azadev.weatherry.R

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

object Constants {

    const val BASE_URL = "http://api.weatherapi.com/v1/"

    fun fromWMO(code: Int): Int {
        return when (code) {
            1000 -> R.drawable.ic_sunny
            1003, 1006, 1030 -> R.drawable.ic_cloudy
            1009 -> R.drawable.ic_overcast
            1063 -> R.drawable.ic_sunnyrainy
            1066, 1117, 1210, 1213, 1255, 1279, 1282 -> R.drawable.ic_snowy
            1069, 1072, 1168, 1171, 1198, 1204, 1207, 1237, 1249, 1252, 1264 -> R.drawable.ic_snowyrainy
            1087 -> R.drawable.ic_thunder
            1114, 1216, 1219, 1222, 1225, 1258, 1261 -> R.drawable.ic_heavysnow
            1135, 1147 -> R.drawable.ic_very_cloudy
            1150, 1153, 1186, 1189, 1240, 1243, 1246 -> R.drawable.ic_rainshower
            1180, 1183, 1192, 1195, 1201 -> R.drawable.ic_rainy
            1273, 1276 -> R.drawable.ic_rainythunder
            else -> R.drawable.ic_sunny
        }
    }
}