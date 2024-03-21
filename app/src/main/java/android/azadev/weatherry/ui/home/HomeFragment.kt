package android.azadev.weatherry.ui.home

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.FragmentHomeBinding
import android.azadev.weatherry.ui.home.pager.adapter.HomePagerAdapter
import android.azadev.weatherry.ui.utils.ForecastType
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/15/2024
 */

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        configurePagerAndTab()

    }

    private fun configurePagerAndTab() {
        val types = ForecastType.entries.toList()
        binding.apply {
            val adapter = HomePagerAdapter(this@HomeFragment, types)
            viewpager.adapter = adapter
            TabLayoutMediator(tab, viewpager) { tab, position ->
                tab.text = types[position].name
            }.attach()
            viewpager.isUserInputEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}