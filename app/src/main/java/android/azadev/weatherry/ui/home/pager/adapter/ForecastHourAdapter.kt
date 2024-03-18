package android.azadev.weatherry.ui.home.pager.adapter

import android.azadev.weatherry.databinding.ItemHourBinding
import android.azadev.weatherry.ui.model.ForecastHour
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

class ForecastHourAdapter(private val forecastHours: List<ForecastHour>) :
    RecyclerView.Adapter<ForecastHourAdapter.HourViewHolder>() {
    inner class HourViewHolder(private val binding: ItemHourBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ForecastHour) {
            binding.apply {
                tvTime.text = data.time
                Glide.with(ivWeatherImage).load(data.icon).into(binding.ivWeatherImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        return HourViewHolder(
            ItemHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.onBind(forecastHours[position])
    }

    override fun getItemCount(): Int = forecastHours.size
}