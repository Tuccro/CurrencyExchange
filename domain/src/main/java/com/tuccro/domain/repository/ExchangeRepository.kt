package com.tuccro.domain.repository

import com.tuccro.domain.model.exchange.ExchangeRequest
import com.tuccro.domain.model.exchange.ExchangeResponse

interface ExchangeRepository {
    suspend fun calculate(exchangeRequest: ExchangeRequest): ExchangeResponse
    suspend fun exchange(exchangeRequest: ExchangeRequest): ExchangeResponse
}