package com.example.weatherz_app

import android.content.Context
import androidx.annotation.UiContext
import androidx.room.Room

object DatabaseProvider {

    fun getDatabase(context: Context) : Database{
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "Weather_database"
        ).build()
    }
}