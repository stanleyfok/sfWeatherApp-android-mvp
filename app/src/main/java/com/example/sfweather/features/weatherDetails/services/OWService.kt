package com.example.sfweather.features.weatherDetails.services

import com.example.sfweather.features.weatherDetails.models.OWResult
import com.example.sfweather.features.weatherDetails.repositories.OWRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class OWService: KoinComponent {
    private val owRepository: OWRepository by inject()

    suspend fun findByCityName(cityName: String): Response<OWResult> {
        return this.owRepository.findByCityName(cityName)
    }

    suspend fun findByCityId(cityId: Int): Response<OWResult> {
        return this.owRepository.findByCityId(cityId)
    }

}