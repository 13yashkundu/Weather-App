package com.example.weatherz_app

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city : String,
        @Query("appId") apiKey : String
    )
}