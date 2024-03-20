package android.azadev.weatherry.ui.home.viewmodel

import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.ForecastData
import android.azadev.weatherry.domain.repository.WeatherRepository
import android.azadev.weatherry.domain.util.DataError
import android.azadev.weatherry.domain.util.Result
import android.azadev.weatherry.ui.utils.NetworkHelper
import android.azadev.weatherry.ui.utils.ViewState
import android.azadev.weatherry.ui.utils.asUiText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/16/2024
 */

class HomeViewModel(
    private val repository: WeatherRepository, private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _currentState = MutableStateFlow<ViewState<CurrentData>>(ViewState.Loading)
    val currentState = _currentState.asStateFlow()

    private val _forecastState = MutableStateFlow<ViewState<ForecastData>>(ViewState.Loading)
    val forecastState = _forecastState.asStateFlow()

    init {
        getForecastWeatherDataByLocation()
        getCurrentWeatherDataByLocation()
    }

    private fun getCurrentWeatherDataByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                when (val result = repository.getCurrentWeatherData("${41.377491},${64.585262}")) {
                    is Result.Error -> {
                        _currentState.value = ViewState.Error(result.error.asUiText())
                    }

                    is Result.Success -> {
                        _currentState.value = ViewState.Success(
                            result.data
                        )
                    }
                }
            } else {
                _currentState.value = ViewState.Error(DataError.Network.NO_INTERNET.asUiText())
            }
        }
    }

    private fun getForecastWeatherDataByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                when (val result = repository.getForecastWeatherData("${41.377491},${64.585262}")) {
                    is Result.Error -> {
                        _forecastState.value = ViewState.Error(result.error.asUiText())
                    }

                    is Result.Success -> {
                        _forecastState.value = ViewState.Success(
                            result.data
                        )
                    }
                }
            } else {
                _forecastState.value = ViewState.Error(DataError.Network.NO_INTERNET.asUiText())
            }
        }
    }


}