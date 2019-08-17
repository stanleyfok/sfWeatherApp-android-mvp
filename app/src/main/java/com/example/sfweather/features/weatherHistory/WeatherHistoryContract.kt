package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory

interface WeatherHistoryContract {
    interface View {
        fun updateView(searchHistories: List<SearchHistory>)
        fun onListFragmentInteraction(searchHistory: SearchHistory)
    }

    interface Presenter {
        suspend fun fetchAllSearchHistories()
    }
}