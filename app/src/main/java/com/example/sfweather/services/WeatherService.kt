package com.example.sfweather.services

import com.example.sfweather.models.OWResult
import com.example.sfweather.models.SearchHistory
import com.example.sfweather.repositories.OWRepository
import com.example.sfweather.repositories.SearchHistoryRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class WeatherService():KoinComponent {
    private val owRepository: OWRepository by inject()
    private val searchHistoryRepository: SearchHistoryRepository by inject()

    suspend fun findWeatherByCityName(cityName: String): Response<OWResult> {
        return this.owRepository.findByCityName(cityName)
    }

    suspend fun findWeatherByCityId(cityId: Int): Response<OWResult> {
        return this.owRepository.findByCityId(cityId)
    }

    suspend fun getAllHistories():List<SearchHistory> {
        return searchHistoryRepository.getAll()
    }

    suspend fun getLatestHistory(): SearchHistory {
        return searchHistoryRepository.getLatest()
    }

    suspend fun insertHistory(searchHistory: SearchHistory) {
        searchHistoryRepository.upsert(searchHistory)
    }

    suspend fun deleteHistoryByCityId(cityId: Int):Int {
        return searchHistoryRepository.deleteByCityId(cityId)
    }
}