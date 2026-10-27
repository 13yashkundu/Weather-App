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

                val response : WeatherResponse? = try { Url_setup.api.getWeather(key, city)
            } catch (e: retrofit2.HttpException) {
                android.util.Log.e("WeatherAPI", "HTTP error: ${e.code()}", e)
                null
            } catch (e: java.io.IOException) {
                android.util.Log.e("WeatherAPI", "Network error", e)
                null
            } catch (e: Exception) {
                android.util.Log.e("WeatherAPI", "Unexpected error", e)
                null
            }finally {
                isLoading = false
                onLoading(false)
            }
            onResult(response)
        }
    }
}