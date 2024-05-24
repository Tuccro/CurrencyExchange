package com.tuccro.domain.store

import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.utils.Result
import kotlinx.coroutines.flow.StateFlow

interface RatesNetworkStore {
    suspend fun getRates(): Result<CurrencyRates>
    fun getCurrencyRatesFlow(): StateFlow<CurrencyRates?>
}