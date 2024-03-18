package android.azadev.weatherry.ui.home.pager.adapter

import android.azadev.weatherry.R
import android.azadev.weatherry.databinding.ItemDayBinding
import android.azadev.weatherry.ui.model.ForecastDayDisplayData
import android.azadev.weatherry.ui.model.ForecastDisplayData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class ForecastDayAdapter(
    private val forecastDays: ForecastDisplayData
) : RecyclerView.Adapter<ForecastDayAdapter.DayViewHolder>() {


    inner class DayViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ForecastDayDisplayData) {
            binding.apply {
                tvDayOfWeek.text = data.dayOfWeek
                tvMaxTemperature.text =
                    root.resources.getString(R.string.text_temperature, data.maxTemp)
                tvMinTemperature.text =
                    root.resources.getString(R.string.text_temperature, data.minTemp)

                val hourAdapter = ForecastHourAdapter(data.hour)
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