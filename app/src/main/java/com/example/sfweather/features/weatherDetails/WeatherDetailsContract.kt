package com.example.sfweather.features.weatherDetails

interface WeatherDetailsContract {
    interface View {
        fun setIsLoading(bool: Boolean)
        fun updateView(data: WeatherDetailsData)
        fun showErrorMessage(errorMessage: String)
    }

    interface Presenter {
        fun fetchLastStoredWeather()
        fun fetchWeatherByCityName(cityName: String)
        fun fetchWeatherByCityId(cityId: Int)
    }
}