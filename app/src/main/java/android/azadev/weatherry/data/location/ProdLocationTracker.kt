package android.azadev.weatherry.data.location

import android.Manifest
import android.app.Application
import android.azadev.weatherry.domain.location.LocationTracker
import android.azadev.weatherry.domain.model.Location
import android.azadev.weatherry.domain.util.DataError
import android.azadev.weatherry.domain.util.Result
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/20/2024
 */

class ProdLocationTracker(
    private val locationClient: FusedLocationProviderClient, private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): Result<Location, DataError.Location> {
        val hasAccessFineLocation = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocation = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        return if (!hasAccessFineLocation || !hasAccessCoarseLocation) {
            Result.Error(DataError.Location.MISSING_PERMISSIONS)
        } else if (!isGpsEnabled) {
            Result.Error(DataError.Location.GPS_FIX_FAILED)
        } else {
            return suspendCancellableCoroutine { cont ->
                var data: Location
                locationClient.lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            data = Location(
                                lat = result.latitude, lon = result.longitude
                            )
                            cont.resume(Result.Success(data))
                        } else {
                            cont.resume(Result.Error(DataError.Location.UNKNOWN))
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        data = Location(
                            lat = it.latitude, lon = it.longitude
                        )
                        cont.resume(Result.Success(data))
                    }
                    addOnFailureListener {
                        cont.resume(Result.Error(DataError.Location.UNKNOWN))
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
            }
        }
    }
}