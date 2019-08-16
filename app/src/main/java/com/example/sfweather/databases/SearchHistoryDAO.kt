package com.example.sfweather.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sfweather.models.SearchHistory

@Dao
interface SearchHistoryDAO {
    @Query("Select * From searchHistory")
    fun getAll(): List<SearchHistory>

    @Query("Select count(cityId) From searchHistory WHERE cityId = :cityId")
    fun getCountByCityId(cityId: Int):Int

    @Insert
    fun insert(vararg searchHistory: SearchHistory)

    @Query("DELETE FROM searchHistory WHERE cityId = :cityId")
    fun deleteByCityId(cityId: Int)
}