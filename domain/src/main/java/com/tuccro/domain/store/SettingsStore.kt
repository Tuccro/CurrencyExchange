package com.tuccro.domain.store

interface SettingsStore {
    fun increaseExchangesCount()
    fun getExchangesCount(): Int
    fun getFirstPaidExchangeNumber(): Int
    fun getExchangeFeePercent(): Double
}