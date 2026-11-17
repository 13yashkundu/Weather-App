package com.example.weatherz_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDAO {
    @Insert
    suspend fun insertHistory(databaseTabel: DatabaseTabel)

    @Query("SELECT * From search_history")
    suspend fun getHistory() : List<DatabaseTabel>

    @Delete
    fun deleteHistory(data : DatabaseTabel)
}