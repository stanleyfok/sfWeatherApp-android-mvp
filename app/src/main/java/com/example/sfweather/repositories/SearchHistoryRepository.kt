package com.example.sfweather.repositories

import android.content.Context
import androidx.room.Room
import com.example.sfweather.databases.AppDB
import com.example.sfweather.models.SearchHistory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchHistoryRepository {

    private val DB_NAME = "searchHistory"
    private var database: AppDB

    constructor(context: Context) {
        this.database = Room.databaseBuilder(
            context,
            AppDB::class.java, DB_NAME
        ).build()
    }

    suspend fun getAll(): List<SearchHistory> {
        return GlobalScope.async {
            database.searchHistoryDao().getAll()
        }.await()
    }

    fun insert(searchHistory: SearchHistory):Job {
        return GlobalScope.launch {
            val count = database.searchHistoryDao().getCountByCityId(searchHistory.cityId)

            if (count == 0) {
                database.searchHistoryDao().insert(searchHistory)
            }
        }
    }

    fun deleteByCityId(cityId: Int):Job {
        return GlobalScope.launch {
            database.searchHistoryDao().deleteByCityId(cityId)
        }
    }
}