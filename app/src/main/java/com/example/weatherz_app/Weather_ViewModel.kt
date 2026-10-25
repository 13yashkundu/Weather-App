package com.example.weatherz_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Weather_ViewModel : ViewModel() {
    fun fetchWeather(key: String, city: String, onResult: (WeatherResponse) -> Unit) {
        viewModelScope.launch {
            val response = Url_setup.api.getWeather(city, key)
            onResult(response) // Response seedha activity ko bhej do
        }
    }
}