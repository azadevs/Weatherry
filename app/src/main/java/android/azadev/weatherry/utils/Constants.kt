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
            113 -> R.drawable.ic_sunny
            116, 119, 143 -> R.drawable.ic_cloudy
            122 -> R.drawable.ic_overcast
            176 -> R.drawable.ic_sunnyrainy
            179, 230, 323, 326, 368, 392, 395 -> R.drawable.ic_snowy
            182, 185, 281, 284, 311, 317, 320, 350, 362, 365, 377 -> R.drawable.ic_snowyrainy
            200 -> R.drawable.ic_thunder
            227, 329, 332, 335, 338, 371, 374 -> R.drawable.ic_heavysnow
            248, 260 -> R.drawable.ic_very_cloudy
            263, 266, 299, 302, 353, 356, 359 -> R.drawable.ic_rainshower
            293, 296, 305, 308, 314 -> R.drawable.ic_rainy
            386, 389 -> R.drawable.ic_rainythunder
            else -> R.drawable.ic_sunny
        }
    }
}