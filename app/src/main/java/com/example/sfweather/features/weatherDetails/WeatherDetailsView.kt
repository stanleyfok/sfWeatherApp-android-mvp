package com.example.sfweather.features.weatherDetails

interface WeatherDetailsView {
    fun setState(state: WeatherDetailsState)
    fun updateView()
    fun showErrorMessage(errorMessage: String)
}