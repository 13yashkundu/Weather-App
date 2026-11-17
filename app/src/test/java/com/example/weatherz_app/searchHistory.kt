package com.example.weatherz_app

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index("city_name", unique = true)
    ]
)

data class SearchHistory(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,

    @ColumnInfo("city_name")
    val cityName : String,
    @Embedded
    val location: Location
)

data class Location (
    val latitude : String
)