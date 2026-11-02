package com.example.weatherz_app

data class WeatherResponse(
    val location: LocationInfo,
    val current: CurrentInfo
)

data class LocationInfo(
    val name: String,
    val country: String,
    val localtime: String
)

data class CurrentInfo(
    val temp_c: Double,
    val temp_f: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val condition: ConditionInfo
)

data class ConditionInfo(
    val text: String,
    val icon: String
)
