package com.example.sfweather.repositories

import com.example.sfweather.databases.SearchHistoryDAO
import com.example.sfweather.models.SearchHistory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.inject
import org.koin.core.KoinComponent

class SearchHistoryRepository: KoinComponent {

    private val dao: SearchHistoryDAO by inject()

    suspend fun getAll(): List<SearchHistory> {
        return GlobalScope.async {
            dao.getAll()
        }.await()
    }

    fun insert(searchHistory: SearchHistory):Job {
        return GlobalScope.launch {
            val count = dao.getCountByCityId(searchHistory.cityId)

            if (count == 0) {
                dao.insert(searchHistory)
            }
        }
    }

    fun deleteByCityId(cityId: Int):Job {
        return GlobalScope.launch {
            dao.deleteByCityId(cityId)
        }
    }
}