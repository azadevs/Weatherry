package android.azadev.weatherry.ui.utils

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/19/2024
 */

sealed interface ViewState<out D> {

    data object Loading : ViewState<Nothing>

    data class Success<D>(val data: D) : ViewState<D>

    data class Error<D>(val error: UiText) : ViewState<D>

}