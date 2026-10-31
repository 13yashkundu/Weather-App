//
//package com.example.weatherz_app
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException
//
//class Weather_ViewModel : ViewModel() {
//
//    var isLoading = false
//        private set
//
//    private val repository = Weaather_Repository()
//
//    fun fetchWeather(
//        key: String,
//        city: String,
//        onLoading: (Boolean) -> Unit,
//        onResult: (WeatherResponse?) -> Unit
//    ) {
//        viewModelScope.launch {
//
//
//
//            isLoading = true
//            onLoading(true)
//
//            val result: WeatherResponse? = try {
//                repository.fetchWeather(key, city)
//            } catch (e: HttpException) {
//                android.util.Log.e(
//                    "WeatherAPI", "HTTP error: ${e.code()}", e
//                )
//                null
//            } catch (e: IOException) {
//                android.util.Log.e(
//                    "WeatherAPI", "Network error", e
//                )
//                null
//            } catch (e: Exception) {
//                android.util.Log.e(
//                    "WeatherAPI", "Unexpected error", e
//                )
//                null
//            } finally {
//                isLoading = false
//                onLoading(false)
//            }
//
//            onResult(result)
//        }
//    }
//}
