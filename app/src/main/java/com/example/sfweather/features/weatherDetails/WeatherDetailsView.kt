package com.example.sfweather.features.weatherDetails

interface WeatherDetailsView {
    fun updateCityName(cityName: String)
    fun updateTemperature(temp: String)
    fun updateWeatherDescription(desc: String)
    fun showErrorMessage(errorMessage: String)
}