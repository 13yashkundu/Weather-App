package com.example.weatherz_app

class Weaather_Repository {
    suspend fun fetchWeather(
        key: String,
        city: String
    ): WeatherResponse? {
        val result : WeatherResponse? =
            Url_setup.api.getWeather(key, city)
        return result
    }
}