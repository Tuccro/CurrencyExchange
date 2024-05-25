package com.tuccro.domain.model.exchange

import com.tuccro.domain.model.CurrencyRates

data class ExchangeRequest(
    val balanceFrom: Double,
    val balanceTo: Double,
    val currencyFrom: String,
    val sum: Double,
    val currencyTo: String,
    val currencyRates: CurrencyRates
)
