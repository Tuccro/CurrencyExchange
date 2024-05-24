package com.tuccro.data.network

import com.tuccro.data.api.CurrencyRatesApi
import com.tuccro.data.network.dto.CurrencyRatesResponseJson
import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.store.RatesNetworkStore
import com.tuccro.domain.utils.Result
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RatesNetworkStoreImpl @Inject constructor(
    private val currencyRatesApi: CurrencyRatesApi
) : RatesNetworkStore {
    private val _currencyRatesFlow = MutableStateFlow<CurrencyRates?>(null)
    private val currencyRatesFlow: StateFlow<CurrencyRates?> = _currencyRatesFlow.asStateFlow()

    override suspend fun getRates(): Result<CurrencyRates> {
        return try {
            val response = currencyRatesApi.getRates()
            val responseBody = response.bodyAsText()

            val currencyRatesResponseJson: CurrencyRatesResponseJson =
                Json.decodeFromString(responseBody)

            val currencyRates = currencyRatesResponseJson.toDomainModel()

            _currencyRatesFlow.value = currencyRates

            Result.Success(currencyRates)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getCurrencyRatesFlow(): StateFlow<CurrencyRates?> {
        return currencyRatesFlow
    }
}