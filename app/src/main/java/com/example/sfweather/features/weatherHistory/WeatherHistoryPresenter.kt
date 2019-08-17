package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.SearchHistoryService
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter: KoinComponent {
    private var view: WeatherHistoryView

    private val searchHistoryService: SearchHistoryService by inject()

    constructor(view: WeatherHistoryView) {
        this.view = view
    }

    suspend fun fetchAllSearchHistories(): List<SearchHistory> {
        return searchHistoryService.getAll()
    }
}