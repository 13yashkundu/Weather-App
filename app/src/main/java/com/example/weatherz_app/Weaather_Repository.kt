package com.example.weatherz_app

class Weaather_Repository {
    suspend fun fetchWeather(
        key: String,
        city: String
    ) : UserData{
        return Url_setup.api.registerUser(UserData("yshkundu", "123456"))
    }
}