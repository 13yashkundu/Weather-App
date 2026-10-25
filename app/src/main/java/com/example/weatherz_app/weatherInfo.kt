package com.example.weatherz_app

data class tempInfo(val humidity : Int, val temp : Double)

data class WeatherResponse(val name : String, val tempInfo: tempInfo)