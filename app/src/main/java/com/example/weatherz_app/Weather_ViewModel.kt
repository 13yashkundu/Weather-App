
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
    val result : WeatherResponse? = null,
    val error : String? = null
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
            var err : String? = null
            _weatherUIState.update {
                it.copy(
                    isLoading = true,
                    error = null)
            }
            val response: WeatherResponse? = try {
                repository.fetchWeather(key, city)

            } catch (e: HttpException) {
                Log.e(
                    "WeatherAPI", "HTTP error: ${e.code()}", e
                )
                err = "Enter valid city name"
                null
            } catch (e: IOException) {
                Log.e(
                    "WeatherAPI", "Network error", e
                )
                err = "Check your internet conection"
                null
            } catch (e: Exception) {
                Log.e(
                    "WeatherAPI", "Unexpected error", e
                )
                err = "Something went wrong, try again later"
                null
            }
                _weatherUIState.update {
                    it.copy(
                        isLoading = false,
                        result = response,
                        error= err

                    )
            }
        }
    }
}