package com.example.sfweather.common.repositories

import com.example.sfweather.common.databases.SearchHistoryDAO
import com.example.sfweather.models.SearchHistory
import org.koin.core.inject
import org.koin.core.KoinComponent

class SearchHistoryRepository: KoinComponent {

    private val dao: SearchHistoryDAO by inject()

    fun getAll(): List<SearchHistory> {
        return dao.getAll()
    }

    fun getLatest(): SearchHistory {
        return dao.getLatest()
    }

    fun upsert(searchHistory: SearchHistory) {
        val count = dao.getCountByCityId(searchHistory.cityId)

        if (count == 0) {
            dao.insert(searchHistory)
        } else {
            dao.update(searchHistory)
        }
    }

    fun deleteByCityId(cityId: Int):Int {
        return dao.deleteByCityId(cityId)
    }
}