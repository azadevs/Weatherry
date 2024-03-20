package android.azadev.weatherry.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

fun parseLongToDate(timestamp: Long): String {
    val locale = Locale.US
    val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
    sdf.timeZone = TimeZone.getDefault()
    val date = Date(timestamp*1000)
    return sdf.format(date)
}

fun parseLongToDateTime(timestamp: Long): String {
    val locale = Locale.US
    val sdf = SimpleDateFormat("HH:mm", locale)
    sdf.timeZone = TimeZone.getDefault()
    val date = Date(timestamp*1000)
    return sdf.format(date)
}

fun parseLongToDayOfWeek(timestamp: Long): String {
    val locale = Locale.getDefault()
    val sdf = SimpleDateFormat("EEEE", locale)
    sdf.timeZone = TimeZone.getDefault()
    val date = Date(timestamp*1000)
    return sdf.format(date)
}