package com.example.weatherz_app

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchHistory::class],
    version = 1
)

abstract class weatherDataBase : RoomDatabase() {
    abstract fun searchHistory() : SearchHistoryDao
}
