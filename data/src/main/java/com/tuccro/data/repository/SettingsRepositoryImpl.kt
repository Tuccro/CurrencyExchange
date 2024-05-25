package com.tuccro.data.repository

import com.tuccro.domain.repository.SettingsRepository
import com.tuccro.domain.store.SettingsStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(private val settingsStore: SettingsStore) :
    SettingsRepository {
    override suspend fun updateSettingsFromServer() {
        // STUB for a future
    }

    override suspend fun getFee(): Double {
        return if (settingsStore.getExchangesCount() + 1 >= settingsStore.getFirstPaidExchangeNumber())
            settingsStore.getExchangeFeePercent()
        else
            0.0
    }

    override suspend fun increaseExchangesCount() {
        settingsStore.increaseExchangesCount()
    }
}