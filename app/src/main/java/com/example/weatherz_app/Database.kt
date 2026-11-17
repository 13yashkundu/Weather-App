package com.example.weatherz_app

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DatabaseTabel::class],
    version = 1
)

abstract class Database : RoomDatabase(){

    abstract class getAccessObject() : DatabaseDAO

}