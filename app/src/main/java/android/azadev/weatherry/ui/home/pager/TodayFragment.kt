package android.azadev.weatherry.ui.home.pager

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentTodayBinding
import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.CurrentDetails
import android.azadev.weatherry.ui.home.HomeFragmentDirections
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
import androidx.navigation.fragment.findNavController
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

    private var currentDetails: CurrentDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodayBinding.bind(view)

        configureObserver()

        binding.tvDetails.setOnClickListener {
            val homeFragmentToDetailsDialogFragment =
                HomeFragmentDirections.actionHomeFragmentToDetailsDialogFragment(
                    currentDetails!!
                )
            findNavController().navigate(homeFragmentToDetailsDialogFragment)
        }
    }

    private fun configureObserver() {
        viewModel.currentState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { result ->
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
        Toast.makeText(
            requireContext(),
            error.asString(requireContext()),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoadingProgress() {
        binding.progressBar.visible()
        binding.ivDownArrow.inVisible()
        binding.ivUpArrow.inVisible()
        binding.tvUnit.inVisible()
        binding.ivSunrise.inVisible()
        binding.ivSunset.inVisible()
    }

    private fun hideLoadingProgress() {
        binding.progressBar.inVisible()
        binding.ivDownArrow.visible()
        binding.ivUpArrow.visible()
        binding.tvUnit.visible()
        binding.ivSunrise.visible()
        binding.ivSunset.visible()
    }

    private fun configureUI(data: CurrentData) {
        binding.apply {
            tvSunriseTime.text = data.sunrise
            tvSunsetTime.text = data.sunset
            tvDate.text = data.date
            tvCurrentTemperature.text = data.currentTemp.toString()
            tvMaxTemperature.text = getString(R.string.text_temperature, data.maxTemp)
            tvMinTemperature.text = getString(R.string.text_temperature, data.minTemp)
            tvCondition.text = data.condition
            ivWeatherImage.setImageResource(data.icon)
            currentDetails = data.details

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        currentDetails = null
    }
}