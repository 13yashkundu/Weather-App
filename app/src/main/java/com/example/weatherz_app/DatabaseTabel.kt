package com.example.weatherz_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class DatabaseTabel (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo("city_name")
    val cityName : String,
    val temparature : String,
    val searchedAt : Long
)