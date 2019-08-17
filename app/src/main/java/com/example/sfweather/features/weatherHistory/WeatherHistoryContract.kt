package com.example.sfweather.features.weatherHistory

import com.example.sfweather.models.SearchHistory

interface WeatherHistoryContract {
    interface View {
        fun updateView(searchHistories: List<SearchHistory>)
        fun onItemViewClick(searchHistory: SearchHistory)
        fun onItemViewSwipe(searchHistory: SearchHistory)
    }

    interface Presenter {
        fun fetchAllSearchHistories()
        fun deleteSearchHistory(searchHistory: SearchHistory)
    }

    interface Adapter {
        fun removeAt(position: Int)
    }
}