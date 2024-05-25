package com.tuccro.domain.repository

interface SettingsRepository {

    suspend fun updateSettingsFromServer()
    suspend fun getFee(): Double
    suspend fun increaseExchangesCount()
}