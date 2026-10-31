package com.example.weatherz_app

import okhttp3.internal.concurrent.Task
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
//    @GET("v1/forecast.json")
//    suspend fun getWeather(
//        @Query("key") apiKey: String,
//        @Query("q") city: String,
//        @Query("days") days: Int = 7,
//        @Query("aqi") aqi: String = "no",
//        @Query("alerts") alerts: String = "no"
//    ): WeatherResponse

    @POST("tasks/task")
    suspend fun postTask(
        @Body task : TaskData
    ):TaskResponse    //Added return typ because server can return details of created task

    @GET("/tasks{taskId}/status")
    suspend fun fetchTask(
        @Path("taskId") taskId : String,
        @Query("status") status : Boolean
    ): List<TaskData>

    @GET("profile")
    suspend fun getProfile(
        @Header("token") token : String
    ) : profileData

}