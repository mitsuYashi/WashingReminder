package com.example.washingreminder.ui.home

import ForecastDay
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.washingreminder.databinding.ItemWeatherBinding
class WeatherAdapter() : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var weatherData: List<ForecastDay> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeatherData(weatherData: List<ForecastDay>) {
        this.weatherData = weatherData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class ViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ForecastDay) {
            // 日付の閾値を超える場合にアイテムを表示
            binding.apply {
                // アイテムのデータをバインディング
                this.weatherDateTextView.text = item.date
                this.weatherMaxTempTextView.text = "最高気温: ${item.day.maxtemp_c}°C"
                this.weatherMinTempTextView.text = "最低気温: ${item.day.mintemp_c}°C"
                this.weatherChanceOfRainTextView .text = "降水確率: ${item.day.daily_chance_of_rain}%"

                // Glide を使用してアイコンを表示
                Glide.with(itemView)
                    .load("https:${item.day.condition.icon}")
                    .into(weatherIconImageView)
            }

        }
    }
}
