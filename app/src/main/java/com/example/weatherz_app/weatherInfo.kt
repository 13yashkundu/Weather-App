package com.example.weatherz_app


data class WeatherResponse(
    val location: LocationInfo,
    val current: CurrentInfo
)

data class LocationInfo(
    val name: String
)

data class CurrentInfo(
    val temp_c: Double,
    val humidity: Int,
    val condition: ConditionInfo
)

data class ConditionInfo(
    val text: String
)// why wetherCondition's type is List<WC> and tempinfo's type is object aaisa kyon dono same hi trike se to data store krr rahe hai