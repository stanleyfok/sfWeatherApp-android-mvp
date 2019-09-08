package com.example.sfweather.repositories

import com.example.sfweather.databases.SearchHistoryDAO
import com.example.sfweather.models.SearchHistory
import org.koin.core.inject
import org.koin.core.KoinComponent

class SearchHistoryRepository: KoinComponent {

    private val dao: SearchHistoryDAO by inject()

    suspend fun getAll(): List<SearchHistory> {
        return dao.getAll()
    }

    suspend fun getLatest(): SearchHistory {
        return dao.getLatest()
    }

    suspend fun upsert(searchHistory: SearchHistory) {
        val count = dao.getCountByCityId(searchHistory.cityId)

        if (count == 0) {
            dao.insert(searchHistory)
        } else {
            dao.update(searchHistory)
        }
    }

    suspend fun deleteByCityId(cityId: Int):Int {
        return dao.deleteByCityId(cityId)
    }
}