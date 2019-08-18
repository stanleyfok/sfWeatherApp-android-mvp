package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.common.services.SearchHistoryService
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter: WeatherHistoryContract.Presenter, KoinComponent {
    private var view:WeatherHistoryContract.View? = null

    private val searchHistoryService: SearchHistoryService by inject()

    override fun attachView(view: WeatherHistoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun fetchAllSearchHistories() {
        CoroutineScope(Dispatchers.Main).launch {
            val searchHistories = withContext(Dispatchers.IO) {
                searchHistoryService.getAll()
            }

            view?.updateView(searchHistories)
        }
    }

    override fun deleteSearchHistory(searchHistory: SearchHistory) {
        CoroutineScope(Dispatchers.IO).launch {
            searchHistoryService.deleteByCityId(searchHistory.cityId)
        }
    }
    //endregion
}