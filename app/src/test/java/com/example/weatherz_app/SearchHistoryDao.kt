package com.example.weatherz_app
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM searchHistory")
    suspend fun getHistory() : List<SearchHistory>

    @Insert
    suspend fun insertHistory(searchHistory: SearchHistory)
}