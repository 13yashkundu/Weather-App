package com.example.weatherz_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Url_setup{
    private val baseUrl = "https://api.weatherapi.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)
    }

