package com.example.sfweather.common.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sfweather.models.SearchHistory

@Dao
interface SearchHistoryDAO {
    @Query("Select * From searchHistory ORDER BY timestamp DESC")
    fun getAll(): List<SearchHistory>

    @Query("Select count(*) From searchHistory ORDER BY timestamp DESC")
    fun countAll(): Int

    @Query("Select * From searchHistory ORDER BY timestamp DESC LIMIT 1")
    fun getLatest(): SearchHistory

    @Query("Select count(cityId) From searchHistory WHERE cityId = :cityId")
    fun getCountByCityId(cityId: Int):Int

    @Insert
    fun insert(vararg searchHistory: SearchHistory)

    @Update
    fun update(vararg searchHistory: SearchHistory)

    @Query("DELETE FROM searchHistory WHERE cityId = :cityId")
    fun deleteByCityId(cityId: Int)
}