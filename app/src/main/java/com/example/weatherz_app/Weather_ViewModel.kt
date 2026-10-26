package com.example.weatherz_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Weather_ViewModel : ViewModel() {
    fun fetchWeather(
        key: String,
        city: String,
        onResult: (WeatherResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = Url_setup.api.getWeather( key,city)
                onResult(response)
            } catch (e: Exception) {
                android.util.Log.e("WeatherAPI", "Failed to load weather", e)
                onResult(null)
            }
        }
    }
}