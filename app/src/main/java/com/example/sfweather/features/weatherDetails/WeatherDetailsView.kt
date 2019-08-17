package com.example.sfweather.features.weatherDetails

interface WeatherDetailsView {
    fun setIsLoading(bool: Boolean)
    fun updateView(data: WeatherDetailsData)
    fun showErrorMessage(errorMessage: String)
}