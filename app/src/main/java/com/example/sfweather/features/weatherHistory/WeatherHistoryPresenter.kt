package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.SearchHistoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter: WeatherHistoryContract.Presenter, KoinComponent {
    private var view: WeatherHistoryContract.View

    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherHistoryContract.View) {
        this.view = view
    }

    override suspend fun fetchAllSearchHistories() {
        val searchHistories = searchHistoryService.getAll()

        this.view.updateView(searchHistories)
    }
}