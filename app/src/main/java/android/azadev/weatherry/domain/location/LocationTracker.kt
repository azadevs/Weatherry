package android.azadev.weatherry.domain.location

import android.azadev.weatherry.domain.model.Location
import android.azadev.weatherry.domain.util.DataError
import android.azadev.weatherry.domain.util.Result

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/19/2024
 */

interface LocationTracker {

    suspend fun getCurrentLocation(): Result<Location, DataError.Location>
}