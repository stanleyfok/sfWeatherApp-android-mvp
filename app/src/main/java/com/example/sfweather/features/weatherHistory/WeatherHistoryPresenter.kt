package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.services.SearchHistoryService
import org.koin.core.KoinComponent
import org.koin.core.inject

class WeatherHistoryPresenter(_view:WeatherHistoryView): KoinComponent {

    private var view: WeatherHistoryView = _view
    private val searchHistoryService: SearchHistoryService by inject()

    suspend fun fetchAllSearchHistories(): List<SearchHistory> {
        return searchHistoryService.getAll()
    }
}