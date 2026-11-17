package com.example.weatherz_app

import android.content.Context

class Weaather_Repository {
    suspend fun fetchWeather(
        key: String,
        city: String
    ): WeatherResponse? {
        val result : WeatherResponse? =
            Url_setup.api.getWeather(key, city)
        return result
    }
}

class SearchHistory(context: Context){

    private val database = DatabaseProvider.getDatabase(context)
    private val dao = database.getAccessObject()

    fun inserHistory(databaseTabel: DatabaseTabel){

        dao.insertHistory(databaseTabel)

    }

    fun deleteHistory(databaseTabel: DatabaseTabel){
        dao.deleteHistory(databaseTabel)
    }
    fun getHistory() : List<DatabaseTabel>{
        return dao.getHistory()
    }
}