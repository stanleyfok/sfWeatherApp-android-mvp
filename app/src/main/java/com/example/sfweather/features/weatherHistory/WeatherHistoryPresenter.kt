package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.features.common.services.SearchHistoryService
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter: WeatherHistoryContract.Presenter, KoinComponent {
    private var view: WeatherHistoryContract.View

    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherHistoryContract.View) {
        this.view = view
    }

    override fun fetchAllSearchHistories() {
        CoroutineScope(Dispatchers.Main).launch {
            val searchHistories = withContext(Dispatchers.IO) {
                searchHistoryService.getAll()
            }

            view.updateView(searchHistories)
        }
    }

    override fun deleteSearchHistory(searchHistory: SearchHistory) {
        CoroutineScope(Dispatchers.IO).launch {
            searchHistoryService.deleteByCityId(searchHistory.cityId)
        }
    }
}