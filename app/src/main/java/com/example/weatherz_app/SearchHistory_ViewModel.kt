package com.example.weatherz_app

import android.content.Context
import androidx.annotation.UiContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class historyData(
    val id : Int = 0,
    val city_name : String? = null,
    val searchedAt : Long? = null,
    val temprature : String? = null
)

class SearchHistory_ViewModel(context: Context) : ViewModel(){
    val _historyVM = MutableStateFlow(historyData())
    val historyVM = _historyVM.asStateFlow()

    private val repository = SearchHistry_Repository(context)


}