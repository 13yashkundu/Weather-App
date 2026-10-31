//package com.example.weatherz_app
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class WeatherViewModelFactory(
//    private val repository: Weaather_Repository
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(
//        modelClass: Class<T>
//    ): T {
//        if (modelClass.isAssignableFrom(Weather_ViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return Weather_ViewModel(repository) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}