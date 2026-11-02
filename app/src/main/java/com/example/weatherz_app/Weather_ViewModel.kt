
package com.example.weatherz_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class WeatherUIState(
    val isLoading : Boolean = false,
    val result : WeatherResponse? = null
)
class Weather_ViewModel : ViewModel() {

    private val _weatherUIState = MutableStateFlow(WeatherUIState())
    val weatherUIState = _weatherUIState.asStateFlow()
    private val repository = Weaather_Repository()

    fun fetchWeather(
        key: String,
        city: String
    ) {
        viewModelScope.launch {
            _weatherUIState.update {
                it.copy(isLoading = true)
            }
            val response: WeatherResponse? = try {
                repository.fetchWeather(key, city)

            } catch (e: HttpException) {
                Log.e(
                    "WeatherAPI", "HTTP error: ${e.code()}", e
                )
                null
            } catch (e: IOException) {
                Log.e(
                    "WeatherAPI", "Network error", e
                )
                null
            } catch (e: Exception) {
                Log.e(
                    "WeatherAPI", "Unexpected error", e
                )
                null
            }
                _weatherUIState.update {
                    it.copy(
                        isLoading = false,
                        result = response
                    )
            }
        }
    }
}