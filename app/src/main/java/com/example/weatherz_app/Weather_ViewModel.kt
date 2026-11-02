
package com.example.weatherz_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Weather_ViewModel : ViewModel() {

    var isLoading = false
        private set

    private val repository = Weaather_Repository()

    fun fetchWeather(
        key: String,
        city: String
    ) {
        viewModelScope.launch {

            val result: WeatherResponse? = try {
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
            } finally {
                isLoading = false
            }
        }
    }
}
//
//
//
//
//            isLoading = true
//            onLoading(true)
//

//
//            onResult(result)
//        }
//    }
//}
