package android.azadev.weatherry.ui.details

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentDialogDetailsBinding
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class DetailsDialogFragment : BottomSheetDialogFragment(R.layout.fragment_dialog_details) {

    private var _binding: FragmentDialogDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDialogDetailsBinding.bind(view)

        configureUI()

    }

    private fun configureUI() {
        val details = args.details
        binding.apply {
            tvHumidity.text = details.humidity.toString()
            tvPrecipitation.text = details.precipitation.toString()
            tvPressure.text = details.pressure.toString()
            tvUv.text = details.uv.toString()
            tvVisibility.text = details.vis.toString()
            tvWind.text = details.wind.toString()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}