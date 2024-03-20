package android.azadev.weatherry.ui.home.pager.adapter

import android.azadev.weatherry.ui.home.pager.AfterFragment
import android.azadev.weatherry.ui.home.pager.TodayFragment
import android.azadev.weatherry.ui.utils.ForecastType
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class HomePagerAdapter(fragment: Fragment, private val title: List<ForecastType>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return title.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (title[position]) {
            ForecastType.Today -> {
                TodayFragment()
            }

            ForecastType.After -> {
                AfterFragment()
            }
        }
    }
}