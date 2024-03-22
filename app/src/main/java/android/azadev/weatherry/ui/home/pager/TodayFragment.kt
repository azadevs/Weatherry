package android.azadev.weatherry.ui.home.pager

import android.Manifest
import android.annotation.SuppressLint
import android.azadev.weatherry.BuildConfig
import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentTodayBinding
import android.azadev.weatherry.domain.model.CurrentData
import android.azadev.weatherry.domain.model.CurrentDetails
import android.azadev.weatherry.ui.WeatherActivity
import android.azadev.weatherry.ui.home.HomeFragmentDirections
import android.azadev.weatherry.ui.home.viewmodel.HomeViewModel
import android.azadev.weatherry.ui.utils.UiExtensions.inVisible
import android.azadev.weatherry.ui.utils.UiExtensions.visible
import android.azadev.weatherry.ui.utils.UiText
import android.azadev.weatherry.ui.utils.ViewState
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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


        if (!checkPermissions()) {
            requestPermissions()
        } else {
            viewModel.getCurrentWeatherDataByLocation()
        }

        configureObserver()

        binding.tvDetails.setOnClickListener {
            val homeFragmentToDetailsDialogFragment =
                HomeFragmentDirections.actionHomeFragmentToDetailsDialogFragment(
                    currentDetails!!
                )
            findNavController().navigate(homeFragmentToDetailsDialogFragment)
        }
    }

    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        handlePermissionResults(results)
    }

    private fun checkPermissions(): Boolean {
        val coarseLocation = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val fineLocation = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return coarseLocation && fineLocation
    }

    @SuppressLint("NewApi")
    private fun handlePermissionResults(results: Map<String, Boolean>) {
        val allGranted = results.values.all { it }
        if (allGranted) {
            viewModel.getCurrentWeatherDataByLocation()
        } else {
            val deniedPermission = results.filter { !it.value }.keys.toList()
            if (shouldShowRequestPermissionRationale(deniedPermission[0])) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Permissions")
                    .setMessage("This app needs Location permission to provide weather updates specific to your area.")
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                        requestPermissions()
                    }.show()

            } else {
                val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    data = uri
                    startActivity(this)
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        permissionsLauncher.launch(permissions)
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
        binding.tvDetails.inVisible()
    }

    private fun hideLoadingProgress() {
        binding.progressBar.inVisible()
        binding.ivDownArrow.visible()
        binding.ivUpArrow.visible()
        binding.tvUnit.visible()
        binding.ivSunrise.visible()
        binding.ivSunset.visible()
        binding.tvDetails.visible()
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
            (activity as WeatherActivity).supportActionBar?.title=data.cityName
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        currentDetails = null
    }
}