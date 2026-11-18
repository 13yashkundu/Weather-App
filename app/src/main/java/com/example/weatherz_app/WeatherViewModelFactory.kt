package com.example.weatherz_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(Weather_ViewModel::class.java)) {

            val repository = SearchHistry_Repository(
                context.applicationContext
            )

            @Suppress("UNCHECKED_CAST")
            return Weather_ViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}