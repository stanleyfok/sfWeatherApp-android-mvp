package com.example.sfweather.features.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.OWApiService
import com.example.sfweather.services.SearchHistoryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherDetailsPresenter: KoinComponent {

    private var view: WeatherDetailsView

    private val owApiService: OWApiService by inject()
    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherDetailsView) {
        this.view = view
    }

    suspend fun fetchLastStoredWeather() {
        val searchHistory = searchHistoryService.getLatest()

        this.fetchWeatherByCityId(searchHistory.cityId)
    }

    fun fetchWeatherByCityName(cityName: String) {
        val call = owApiService.findByCityName(cityName)

        fetchWeather(call)
    }

    fun fetchWeatherByCityId(cityId: Int) {
        val call = owApiService.findByCityId(cityId)

        fetchWeather(call)
    }

    private fun fetchWeather(call: Call<OWResult>) {
        this.view.setIsLoading(true)

        call.enqueue(object: Callback<OWResult> {
            override fun onResponse(call: Call<OWResult>, response: Response<OWResult>) {
                if (response.isSuccessful) {
                    val owResult = response.body()!!

                    // update view
                    val weatherDetailsData = WeatherDetailsData(owResult)
                    view.updateView(weatherDetailsData)

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

                view.setIsLoading(false)
            }

            override fun onFailure(call: Call<OWResult>, t: Throwable) {
                t.message?.let {
                    view.showErrorMessage(it)
                } ?: run {
                    view.showErrorMessage("Unknown Error")
                }

                view.setIsLoading(false)
            }
        })
    }

    private fun insertSearchHistory(owResult: OWResult) {
        val timestamp = System.currentTimeMillis() / 1000;
        val searchHistory = SearchHistory(owResult.id, owResult.name, timestamp)

        this.searchHistoryService.insert(searchHistory)
    }
}