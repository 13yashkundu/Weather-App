package com.example.weatherz_app

import okio.Sink

data class WeatherResponse(
    val location: LocationInfo,
    val current: CurrentInfo,
    val forecastResponse: forecastResponse
)

data class LocationInfo(
    val name: String,
    val country: String,
    val localtime: String
)

data class CurrentInfo(
    val temp_c: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val condition: ConditionInfo
)

data class ConditionInfo(
    val text: String,
    val icon: String
)

data class forecastResponse(
    val forecast : Forecast
)

data class Forecast(
    val forecastday : List<ForecastDay>
)

data class ForecastDay(
    val day : Day,
    val astro : Astro
)

data class Astro(
    val sunrise : String,
    val sunset : String
)

data class Day(
    val maxtemp_c : Double,
    val mintemp_c : Double,
    val condition : Condition
)

data class Condition(
    val text : String,
    val icon : String
)
