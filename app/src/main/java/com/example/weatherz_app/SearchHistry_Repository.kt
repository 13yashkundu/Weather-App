package com.example.weatherz_app

import android.content.Context


class SearchHistry_Repository(context: Context){

    private val database = DatabaseProvider.getDatabase(context)
    private val dao = database.getAccessObject()

    suspend fun inserHistory(databaseTabel: DatabaseTabel){

        dao.insertHistory(databaseTabel)

    }

    fun deleteHistory(databaseTabel: DatabaseTabel){
        dao.deleteHistory(databaseTabel)
    }
    suspend fun getHistory() : List<DatabaseTabel>{
        return dao.getHistory()
    }
}