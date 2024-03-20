package android.azadev.weatherry.ui.utils

import android.azadev.weatherry.R
import android.azadev.weatherry.domain.util.DataError
import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/19/2024
 */

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()


    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Location.MISSING_PERMISSIONS -> UiText.StringResource(
            R.string.missing_permission
        )

        DataError.Location.LOCATION_DISABLED -> UiText.StringResource(
            R.string.location_disabled
        )

        DataError.Location.GPS_FIX_FAILED -> UiText.StringResource(
            R.string.gps_fix_failed
        )

        DataError.Location.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.time_out
        )

        DataError.Network.TOO_MANY_REQUEST -> UiText.StringResource(
            R.string.too_many_request
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )
    }
}
