package com.tuccro.data.network.dto

import com.tuccro.domain.model.CurrencyRates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRatesResponseJson(
    @SerialName("base")
    val baseCurrency: String,
    val date: String,
    val rates: Map<String, Double>
) {
    fun toDomainModel(): CurrencyRates {
        return CurrencyRates(
            baseCurrency = this.baseCurrency,
            date = this.date,
            rates = this.rates
        )
    }
}