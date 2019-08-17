package com.example.sfweather.features.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.OWApiService
import com.example.sfweather.services.SearchHistoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherDetailsPresenter: KoinComponent, WeatherDetailsContract.Presenter {

    private var view: WeatherDetailsContract.View

    private val owApiService: OWApiService by inject()
    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherDetailsContract.View) {
        this.view = view
    }

    override fun fetchLastStoredWeather() {
        GlobalScope.launch {
            val searchHistory = searchHistoryService.getLatest()

            if (searchHistory != null) {
                fetchWeatherByCityId(searchHistory.cityId)
            }
        }
    }

    override fun fetchWeatherByCityName(cityName: String) {
        this.setViewLoading(true)

        GlobalScope.launch {
            val response = owApiService.findByCityName(cityName)

            handleResponse(response)
        }
    }

    override fun fetchWeatherByCityId(cityId: Int) {
        this.setViewLoading(true)

        GlobalScope.launch {
            val response = owApiService.findByCityId(cityId)

            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<OWResult>) {
        try {
            if (response.isSuccessful) {
                val owResult = response.body()!!

                // update view

                val weatherDetailsData = WeatherDetailsData(owResult)
                updateView(weatherDetailsData)

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
            setViewLoading(false)
        }
    }

    private fun setViewLoading(bool: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            view.setIsLoading(bool)
        }
    }

    private fun updateView(weatherDetailsData: WeatherDetailsData) {
        GlobalScope.launch(Dispatchers.Main) {
            view.updateView(weatherDetailsData)
        }
    }

    private fun insertSearchHistory(owResult: OWResult) {
        val timestamp = System.currentTimeMillis() / 1000;
        val searchHistory = SearchHistory(owResult.id, owResult.name, timestamp)

        this.searchHistoryService.insert(searchHistory)
    }
}