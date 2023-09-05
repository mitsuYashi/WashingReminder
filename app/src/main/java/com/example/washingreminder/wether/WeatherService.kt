package com.example.washingreminder.wether

import WeatherData
import android.annotation.SuppressLint
import com.example.washingreminder.BuildConfig.WEATHER_API_KEY
import com.example.washingreminder.utils.HttpAccessor
import com.fasterxml.jackson.databind.ObjectMapper

class WeatherService {
    @SuppressLint("SimpleDateFormat")
    suspend fun getWeather(lat: Double, lng: Double): WeatherData? {
        val httpAccessor = HttpAccessor()
        val resultJson = httpAccessor.getJson("https://api.weatherapi.com/v1/forecast.json?key=${WEATHER_API_KEY}&q=${lat},%20${lng}&days=7&lang=ja")

        val objectMapper = ObjectMapper()
        val jsonString = resultJson.toString()

        return objectMapper.readValue(jsonString, WeatherData::class.java)
    }

}