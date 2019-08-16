package com.example.sfweather.services

import android.content.Context
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.repositories.SearchHistoryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchHistoryService {
    private var searchHistoryRepository: SearchHistoryRepository

    constructor(context: Context) {
        this.searchHistoryRepository = SearchHistoryRepository(context)
    }

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