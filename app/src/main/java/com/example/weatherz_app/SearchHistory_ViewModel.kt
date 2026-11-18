package com.example.weatherz_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class historyData(
    val id: Int = 0,
    val city_name: String? = null,
    val searchedAt: Long? = null,
    val temprature: String? = null
)

class SearchHistory_ViewModel( val repository: SearchHistry_Repository) : ViewModel() {

    private val _historyList = MutableStateFlow<List<DatabaseTabel>>(emptyList())
    val historyList = _historyList.asStateFlow()


    fun insertData(dataTabel: DatabaseTabel){
        viewModelScope.launch {
                repository.insertHistory(dataTabel)
            fetchHistory()
        }
    }

    fun fetchHistory(){
        viewModelScope.launch {
            val dataList : List<DatabaseTabel> = repository.getHistory()
            _historyList.value = dataList
        }

    }

    fun deleteHistory(data: DatabaseTabel){
        repository.deleteHistory(data)
        fetchHistory()
    }

}