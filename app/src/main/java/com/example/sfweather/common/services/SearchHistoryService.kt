package com.example.sfweather.common.services

import com.example.sfweather.common.models.SearchHistory
import com.example.sfweather.common.repositories.SearchHistoryRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchHistoryService():KoinComponent {
    private val searchHistoryRepository: SearchHistoryRepository by inject()

    fun getAll():List<SearchHistory> {
        return searchHistoryRepository.getAll()
    }

    fun getLatest(): SearchHistory {
        return searchHistoryRepository.getLatest()
    }

    fun insert(searchHistory: SearchHistory) {
        searchHistoryRepository.upsert(searchHistory)
    }

    fun deleteByCityId(cityId: Int):Int {
        return searchHistoryRepository.deleteByCityId(cityId)
    }
}