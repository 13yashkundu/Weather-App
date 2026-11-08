package com.example.weatherz_app

import kotlinx.coroutines.delay

class Weaather_Repository {
    suspend fun fetchWeather(
        key: String,
        city: String
    ): WeatherResponse? {
        delay(2000)
        val result : WeatherResponse? =
            Url_setup.api.getWeather(key, city)
        return result
    }
}