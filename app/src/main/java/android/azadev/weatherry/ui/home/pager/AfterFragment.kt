package android.azadev.weatherry.ui.home.pager

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentAfterBinding
import android.azadev.weatherry.domain.model.ForecastData
import android.azadev.weatherry.ui.home.pager.adapter.ForecastDayAdapter
import android.azadev.weatherry.ui.home.viewmodel.HomeViewModel
import android.azadev.weatherry.ui.utils.UiExtensions.inVisible
import android.azadev.weatherry.ui.utils.UiExtensions.visible
import android.azadev.weatherry.ui.utils.UiText
import android.azadev.weatherry.ui.utils.ViewState
import android.os.Bundle
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
                    is ViewState.Error -> {
                        hideLoadingProgress()
                        showErrorMessage(result.error)
                    }

                    ViewState.Loading -> {
                        showLoadingProgress()
                    }

                    is ViewState.Success -> {
                        hideLoadingProgress()
                        configureUI(result.data)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrorMessage(error: UiText) {
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoadingProgress() {
        binding.progressBar.visible()
    }

    private fun hideLoadingProgress() {
        binding.progressBar.inVisible()
    }

    private fun configureUI(data: ForecastData) {
        val adapter = ForecastDayAdapter(data.forecast)
        binding.rvForecast.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}