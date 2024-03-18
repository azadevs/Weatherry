package android.azadev.weatherry.utils

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

sealed class Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(val data: String) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()

}