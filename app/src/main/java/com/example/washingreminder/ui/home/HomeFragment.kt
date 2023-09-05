package com.example.washingreminder.ui.home

import WeatherData
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.washingreminder.R
import com.example.washingreminder.databinding.FragmentHomeBinding
import com.example.washingreminder.wether.WeatherService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private val weatherService = WeatherService()
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var barChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.weatherRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        weatherAdapter = WeatherAdapter()
        recyclerView.adapter = weatherAdapter

        val weatherSurfaceView = binding.weatherSurfaceView

        barChart = binding.barChart
        setupBarChart()

        // ViewModelからLiveDataを監視し、データが変更されたらUIに反映
        homeViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            // 天気情報をSurfaceViewにセット
            weatherSurfaceView.setForecastday(weatherData.forecast.forecastday[0])
            // 天気情報をAdapterにセット
            weatherAdapter.setWeatherData(weatherData.forecast.forecastday.filter { it.date != weatherData.forecast.forecastday[0].date })
            // 天気情報をグラフにセット
            updateBarChart(getChartData(weatherData))

        }

        // WeatherDataを非同期で取得
        fetchWeatherData()

        return root
    }

    // ...

    private fun fetchWeatherData() {
        val lat = 35.153153153153156 // データベースから取得した緯度
        val lng = 136.873445329424 // データベースから取得した経度

        // 非同期でWeatherDataを取得
        lifecycleScope.launch {
            val weatherData = weatherService.getWeather(lat, lng)
            if (weatherData != null) {
                homeViewModel.setWeatherData(weatherData)
            }
        }
    }

    private fun setupBarChart() {
        barChart.description.isEnabled = false
        barChart.setNoDataText("No data available")
        barChart.setTouchEnabled(false)

        // X軸の設定
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = 45f

        // Y軸の設定
        val leftAxis = barChart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 100f // 降水確率の最大値を設定
        leftAxis.setDrawGridLines(false)

        // グラフ下の目盛りの設定
        val rightAxis = barChart.axisRight
        rightAxis.isEnabled = false

        // カスタムマーカービューの設定
        val mv = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = barChart
        barChart.marker = mv
    }

    private fun getChartData(weatherData: WeatherData): List<BarEntry> {
        val entries = mutableListOf<BarEntry>()

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val forecastday = weatherData.forecast.forecastday[0]
        val forecastday2 = weatherData.forecast.forecastday[1]

        val hourData = forecastday.hour.filter { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN).parse(it.time)!! >= SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN).parse(
            calendar.get(Calendar.YEAR).toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" + calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + hour.toString() + ":00"
        ) }
            .toMutableList()

        hourData.addAll(forecastday2.hour)

        for ((index, hour) in hourData.withIndex()) {
            if (index > 24) {
                break
            }
            val precipChance = hour.chance_of_rain.toFloat()
            entries.add(BarEntry(index.toFloat(), precipChance))
        }

        return entries
    }

    private fun updateBarChart(chartData: List<BarEntry>) {
        val barDataSet = BarDataSet(chartData, "降水確率")
        barDataSet.color = Color.BLUE
        barDataSet.valueTextColor = Color.BLUE
        barDataSet.axisDependency = YAxis.AxisDependency.LEFT
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}%" // 降水確率を%で表示
            }
        }

        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(barDataSet)

        val data = BarData(dataSets)
        barChart.data = data
        barChart.invalidate()

        // X軸の設定
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR_OF_DAY) + value.toInt()
                if (hour > 24) {
                    hour -= 24
                }

                return "$hour:00"
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
