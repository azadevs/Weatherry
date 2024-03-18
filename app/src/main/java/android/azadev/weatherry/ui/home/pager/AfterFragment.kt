package android.azadev.weatherry.ui.home.pager

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentAfterBinding
import android.azadev.weatherry.ui.model.ForecastDisplayData
import android.azadev.weatherry.ui.home.pager.adapter.ForecastDayAdapter
import android.azadev.weatherry.ui.home.viewmodel.HomeViewModel
import android.azadev.weatherry.utils.Resource
import android.azadev.weatherry.utils.UiExtensions.inVisible
import android.azadev.weatherry.utils.UiExtensions.visible
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class AfterFragment : Fragment(R.layout.fragment_after) {
    private var _binding: FragmentAfterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAfterBinding.bind(view)

        configureObserver()


    }

    private fun configureObserver() {
        viewModel.forecastState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        hideLoadingProgress()
                        showErrorMessage(result.data)
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

    private fun showErrorMessage(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingProgress() {
        binding.progressBar.visible()
    }

    private fun hideLoadingProgress() {
        binding.progressBar.inVisible()
    }

    private fun configureUI(data: ForecastDisplayData) {
        val adapter = ForecastDayAdapter(data)
        binding.rvForecast.adapter = adapter
        Log.d("TAG10 ", "getForecastWeatherDataByLocation: ${data.forecast.last().dayOfWeek}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}