package com.example.weatherz_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Weather_ViewModel : ViewModel(){
    fun fetchWeather(key : String, city : String){
        viewModelScope.launch {
            val response = Url_setup.api.getWeather(city,key)
            println(response)
        }
    }
}