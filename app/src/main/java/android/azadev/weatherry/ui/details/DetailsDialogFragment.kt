package android.azadev.weatherry.ui.details

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentDialogDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class DetailsDialogFragment : BottomSheetDialogFragment(R.layout.fragment_dialog_details) {
    private var _binding: FragmentDialogDetailsBinding? = null
    private val binding get() = _binding!!



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}