package com.example.washingreminder.ui.home

import WeatherData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel() : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()

    val weatherData: LiveData<WeatherData> = _weatherData

    fun setWeatherData(weatherData: WeatherData) {
        _weatherData.value = weatherData
    }
//    fun updateWeatherData(data: WeatherData) {
//        _weatherData.value = data
//    }
//
//    suspend fun getOne(): Place {
//        return placeRepository.getOne()
//    }

}