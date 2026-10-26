package com.example.weatherz_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Weather_ViewModel : ViewModel() {
    var isLoading = false
        private set
    fun fetchWeather(
        key: String,
        city: String,
        onLoading: (Boolean) -> Unit,
        onResult: (WeatherResponse?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            onLoading(true)
            try {
                val response = Url_setup.api.getWeather(key, city)
                onResult(response)
            } catch (e: retrofit2.HttpException) {
                android.util.Log.e("WeatherAPI", "HTTP error: ${e.code()}", e)
                onResult(null)
            } catch (e: java.io.IOException) {
                android.util.Log.e("WeatherAPI", "Network error", e)
                onResult(null)
            } catch (e: Exception) {
                android.util.Log.e("WeatherAPI", "Unexpected error", e)
                onResult(null)
            }finally {
                isLoading = false
                onLoading(false)
            }
        }
    }
}