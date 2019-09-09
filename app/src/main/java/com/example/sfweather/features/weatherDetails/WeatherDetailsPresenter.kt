package com.example.sfweather.features.weatherDetails

import com.example.sfweather.models.OWApiError
import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.repositories.WeatherRepository
import kotlinx.coroutines.*
import retrofit2.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherDetailsPresenter: KoinComponent, WeatherDetailsContract.Presenter {
    private var view: WeatherDetailsContract.View? = null

    private val weatherRepository: WeatherRepository by inject()

    override fun attachView(view: WeatherDetailsContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun fetchLastStoredWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val searchHistory = weatherRepository.getLatestHistory()

            if (searchHistory != null) {
                fetchWeatherByCityId(searchHistory.cityId)
            }
        }
    }

    override fun fetchWeatherByCityName(cityName: String) {
        view?.setIsLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherRepository.findWeatherByCityName(cityName)

            handleResponse(response)
        }
    }

    override fun fetchWeatherByCityId(cityId: Int) {
        view?.setIsLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherRepository.findWeatherByCityId(cityId)

            handleResponse(response)
        }
    }
    //endregion

    //region private methods
    private fun handleResponse(response: Response<OWResult>) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (response.isSuccessful) {
                    val owResult = response.body()!!

                    // update view

                    val weatherDetailsData = WeatherDetailsData(owResult)
                    view?.updateView(weatherDetailsData)

                    // store to db
                    insertSearchHistory(owResult)
                } else {
                    val apiError = OWApiError.createFromResponse(response.errorBody()!!)

                    view?.showErrorMessage(apiError.message)
                }
            } catch (e: Exception) {
                e.message?.let {
                    view?.showErrorMessage(it)
                } ?: run {
                    view?.showErrorMessage("Unknown Error")
                }
            } finally {
                view?.setIsLoading(false)
            }
        }
    }

    private fun insertSearchHistory(owResult: OWResult) {
        val timestamp = System.currentTimeMillis() / 1000;
        val searchHistory = SearchHistory(owResult.id, owResult.name, timestamp)

        CoroutineScope(Dispatchers.IO).launch {
            weatherRepository.insertHistory(searchHistory)
        }
    }
    //endregion
}