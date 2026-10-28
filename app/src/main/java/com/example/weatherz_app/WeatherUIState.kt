package com.example.weatherz_app

sealed interface WeatherUiState {

    data object Loading : WeatherUiState

    data class Success(
        val weather: WeatherResponse
    ) : WeatherUiState

    data class Error(
        val message: String
    ) : WeatherUiState
}