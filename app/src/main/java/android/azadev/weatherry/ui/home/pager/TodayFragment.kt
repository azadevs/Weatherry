package android.azadev.weatherry.ui.home.pager

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentTodayBinding
import android.azadev.weatherry.ui.home.viewmodel.HomeViewModel
import android.azadev.weatherry.ui.model.CurrentWeatherDisplayData
import android.azadev.weatherry.utils.Resource
import android.azadev.weatherry.utils.UiExtensions.inVisible
import android.azadev.weatherry.utils.UiExtensions.visible
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class TodayFragment : Fragment(R.layout.fragment_today) {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodayBinding.bind(view)

        configureObserver()
    }

    private fun configureObserver() {
        viewModel.currentState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    hideLoadingProgress()
                    Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    showLoadingProgress()
                }

                is Resource.Success -> {
                    hideLoadingProgress()
                    configureUI(result.data)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun showLoadingProgress() {
        binding.progressBar.visible()
        binding.cardView.visible()
    }

    private fun hideLoadingProgress() {
        binding.progressBar.inVisible()
        binding.cardView.inVisible()
    }

    private fun configureUI(weatherData: CurrentWeatherDisplayData) {
        binding.apply {
            tvSunriseTime.text = weatherData.sunrise
            tvSunsetTime.text = weatherData.sunset
            tvDate.text = weatherData.date
            tvCurrentTemperature.text = weatherData.currentTemp.toString()
            tvMaxTemperature.text = getString(R.string.text_temperature, weatherData.maxTemp)
            tvMinTemperature.text = getString(R.string.text_temperature, weatherData.minTemp)
            tvCondition.text = weatherData.condition
            Glide.with(ivWeatherImage).load("https://cdn.weatherapi.com/weather/64x64/day/176.png")
                .into(ivWeatherImage)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}