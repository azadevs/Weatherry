package android.azadev.weatherry.domain.util


/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/19/2024
 */

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        UNKNOWN
    }

    enum class Location : DataError {
        MISSING_PERMISSIONS,
        LOCATION_DISABLED,
        GPS_FIX_FAILED,
        UNKNOWN
    }
}
