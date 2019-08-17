package com.example.sfweather.services

import com.example.sfweather.models.SearchHistory
import com.example.sfweather.repositories.SearchHistoryRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchHistoryService():KoinComponent {
    private val searchHistoryRepository: SearchHistoryRepository by inject()

    suspend fun getAll():List<SearchHistory> {
        return searchHistoryRepository.getAll()
    }

    fun insert(searchHistory: SearchHistory) {
        searchHistoryRepository.insert(searchHistory)
    }

    fun deleteByCityId(cityId: Int) {
        searchHistoryRepository.deleteByCityId(cityId)
    }
}