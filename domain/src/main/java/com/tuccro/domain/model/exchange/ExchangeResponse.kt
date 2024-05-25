package com.tuccro.domain.model.exchange

data class ExchangeResponse(
    val resultSum: Double,
    val fee: Double,
    val status: Status
)

enum class Status {
    OK, NOT_ENOUGH_MONEY
}
