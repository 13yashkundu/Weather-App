package com.example.weatherz_app

data class main(val humidity : Int, val temp : Double)

data class WeatherCondition(val main : String, val description : String)

data class WeatherResponse(val name : String, val tempInfo: main, val weatherCondition: List<WeatherCondition>)// why wetherCondition's type is List<WC> and tempinfo's type is object aaisa kyon dono same hi trike se to data store krr rahe hai