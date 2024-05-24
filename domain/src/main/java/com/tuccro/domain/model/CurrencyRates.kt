package com.tuccro.domain.model

data class CurrencyRates(
    val baseCurrency: String,
    val date: String,
    val rates: Map<String, Double>
)