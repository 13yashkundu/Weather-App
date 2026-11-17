package com.example.weatherz_app

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun getDatabase(context : Context) : weatherDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            weatherDataBase::class.java,
            "weather_database"
        ).build()
    }
}