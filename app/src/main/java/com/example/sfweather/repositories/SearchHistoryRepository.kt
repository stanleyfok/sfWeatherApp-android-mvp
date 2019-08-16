package com.example.sfweather.repositories

import android.content.Context
import androidx.room.Room
import com.example.sfweather.databases.AppDB
import com.example.sfweather.models.SearchHistory

class SearchHistoryRepository {

    private val DB_NAME = "searchHistory"
    private var database: AppDB

    constructor(context: Context) {
        this.database = Room.databaseBuilder(
            context,
            AppDB::class.java, DB_NAME
        ).build()
    }

    fun getAll(): List<SearchHistory> {

        return this.database.searchHistoryDao().getAll()
    }

    fun insert(searchHistory: SearchHistory) {
        val count = this.database.searchHistoryDao().getCountByCityId(searchHistory.cityId)

        if (count == 0) {
            this.database.searchHistoryDao().insert(searchHistory)
        }
    }

    fun deleteByCityId(cityId: Int) {
        this.database.searchHistoryDao().deleteByCityId(cityId)
    }
}