package android.azadev.weatherry.ui.home.pager.adapter

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.ItemDayBinding
import android.azadev.weatherry.ui.model.ForecastDayDisplayData
import android.azadev.weatherry.ui.model.ForecastDisplayData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class ForecastDayAdapter(
    private val forecastDays: ForecastDisplayData
) : RecyclerView.Adapter<ForecastDayAdapter.DayViewHolder>() {
    val pool = RecycledViewPool()

    inner class DayViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ForecastDayDisplayData) {
            binding.apply {
                tvDayOfWeek.text = data.dayOfWeek
                tvMaxTemperature.text =
                    root.resources.getString(R.string.text_temperature, data.maxTemp)
                tvMinTemperature.text =
                    root.resources.getString(R.string.text_temperature, data.minTemp)

                Glide.with(root).load("https:${data.icon}").into(ivWeatherImage)

                val hourAdapter = ForecastHourAdapter(data.hour)
                rvHour.setRecycledViewPool(pool)
                rvHour.adapter = hourAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.onBind(forecastDays.forecast[position])
    }

    override fun getItemCount(): Int = forecastDays.forecast.size
}