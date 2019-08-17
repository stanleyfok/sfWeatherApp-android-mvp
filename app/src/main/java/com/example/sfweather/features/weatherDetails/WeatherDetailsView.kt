package com.example.sfweather.features.weatherDetails

interface WeatherDetailsView {
    fun setState(state: WeatherDetailsState)
    fun setIsLoading(bool: Boolean)
    fun updateView()
    fun showErrorMessage(errorMessage: String)
}