package com.example.sfweather.features.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.features.weatherDetails.services.OWService
import com.example.sfweather.common.services.SearchHistoryService
import kotlinx.coroutines.*
import retrofit2.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherDetailsPresenter: KoinComponent, WeatherDetailsContract.Presenter {

    private var view: WeatherDetailsContract.View

    private val owService: OWService by inject()
    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherDetailsContract.View) {
        this.view = view
    }

    override fun fetchLastStoredWeather() {
        CoroutineScope(Dispatchers.Main).launch {
            val searchHistory = withContext(Dispatchers.IO) {
                searchHistoryService.getLatest()
            }

            if (searchHistory != null) {
                fetchWeatherByCityId(searchHistory.cityId)
            }
        }
    }

    override fun fetchWeatherByCityName(cityName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            view.setIsLoading(true)

            val response = withContext(Dispatchers.IO) {
                owService.findByCityName(cityName)
            }

            handleResponse(response)
        }
    }

    override fun fetchWeatherByCityId(cityId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            view.setIsLoading(true)

            val response = withContext(Dispatchers.IO) {
                owService.findByCityId(cityId)
            }

            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<OWResult>) {
        try {
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
        } catch (e: Exception) {
            e.message?.let {
                view.showErrorMessage(it)
            } ?: run {
                view.showErrorMessage("Unknown Error")
            }
        } finally {
            view.setIsLoading(false)
        }
    }

    private fun insertSearchHistory(owResult: OWResult) {
        val timestamp = System.currentTimeMillis() / 1000;
        val searchHistory = SearchHistory(owResult.id, owResult.name, timestamp)

        this.searchHistoryService.insert(searchHistory)
    }
}