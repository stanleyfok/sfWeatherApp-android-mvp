package com.example.sfweather.features.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.OWApiService
import com.example.sfweather.services.SearchHistoryService
import com.example.sfweather.utils.WeatherUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt
import org.koin.core.KoinComponent
import org.koin.core.inject


class WeatherDetailsPresenter(_view: WeatherDetailsView): KoinComponent {

    private var view: WeatherDetailsView = _view
    private val searchHistoryService: SearchHistoryService by inject()

    fun fetchWeatherByCityName(cityName: String) {
        val apiClient = OWApiService.create()
        val call = apiClient.findByCityName(cityName)

        fetchWeather(call)
    }

    fun fetchWeatherByCityId(cityId: Int) {
        val apiClient = OWApiService.create()
        val call = apiClient.findByCityId(cityId)

        fetchWeather(call)
    }

    fun fetchWeather(call: Call<OWResult>) {
        call.enqueue(object: Callback<OWResult> {
            override fun onResponse(call: Call<OWResult>, response: Response<OWResult>) {
                if (response.isSuccessful) {
                    val owResult = response.body()!!

                    // update view
                    updateView(owResult)

                    // store to db
                    insertSearchHistory(owResult)
                } else {
                    try {
                        val apiError = OWApiError.createFromResponse(response.errorBody()!!)

                        view.showErrorMessage(apiError.message)
                    } catch (e:Exception) {
                        view.showErrorMessage(if (!e.message.isNullOrEmpty()) e.message!! else "Unknown Error")
                    }
                }
            }

            override fun onFailure(call: Call<OWResult>, t: Throwable) {
                println(t.message)

                //TODO: more detailed error?
                view.showErrorMessage("Unknown Error")
            }
        })
    }



    private fun updateView(owResult:OWResult) {
        view.updateCityName(owResult.name)

        val displayTemp = WeatherUtils.kelvinToCelsius(owResult.main.temp).roundToInt().toString() + "°"
        view.updateTemperature(displayTemp)

        if (owResult.weather.isNotEmpty()) {
            view.updateWeatherDescription(owResult.weather[0].main)
        }

    }

    private fun insertSearchHistory(owResult: OWResult) {
        val timestamp = System.currentTimeMillis() / 1000;
        val searchHistory = SearchHistory(owResult.id, owResult.name, timestamp)

        this.searchHistoryService.insert(searchHistory)
    }
}