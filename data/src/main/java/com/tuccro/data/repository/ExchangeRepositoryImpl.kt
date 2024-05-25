package com.tuccro.data.repository

import com.tuccro.domain.model.Balance
import com.tuccro.domain.model.exchange.ExchangeRequest
import com.tuccro.domain.model.exchange.ExchangeResponse
import com.tuccro.domain.model.exchange.Status
import com.tuccro.domain.repository.ExchangeRepository
import com.tuccro.domain.repository.SettingsRepository
import com.tuccro.domain.repository.WalletRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val walletRepository: WalletRepository
) :
    ExchangeRepository {
    override suspend fun calculate(exchangeRequest: ExchangeRequest): ExchangeResponse {
        val baseCurrency = exchangeRequest.currencyRates.baseCurrency

        val rate = calculateRate(
            exchangeRequest.currencyFrom,
            exchangeRequest.currencyTo,
            exchangeRequest.currencyRates.rates,
            baseCurrency
        )

        val feePercent = settingsRepository.getFee()

        val resultSum = exchangeRequest.sum.times(rate)
        val feeSum = exchangeRequest.sum.times(feePercent).div(100.0)
        val status = if (exchangeRequest.sum + feeSum <= exchangeRequest.balanceFrom)
            Status.OK else Status.NOT_ENOUGH_MONEY

        return ExchangeResponse(
            resultSum,
            feeSum,
            status
        )
    }

    override suspend fun exchange(exchangeRequest: ExchangeRequest): ExchangeResponse {
        val result = calculate(exchangeRequest)

        return when (result.status) {
            Status.NOT_ENOUGH_MONEY -> result
            Status.OK -> {
                val newBalanceFrom = exchangeRequest.balanceFrom - exchangeRequest.sum - result.fee
                val newBalanceTo = exchangeRequest.balanceTo + result.resultSum

                walletRepository.insertBalance(
                    Balance(exchangeRequest.currencyFrom, newBalanceFrom)
                )
                walletRepository.insertBalance(
                    Balance(exchangeRequest.currencyTo, newBalanceTo)
                )
                result
            }
        }
    }

    private fun calculateRate(
        currencyFrom: String,
        currencyTo: String,
        rates: Map<String, Double>,
        base: String
    ): Double {
        return if (currencyFrom == base)
            rates.getValue(currencyTo)
        else if (currencyTo == base)
            rates.getValue(currencyFrom)
        else {
            // calculate cross-rate
            rates.getValue(currencyFrom).times(rates.getValue(currencyTo))
        }
    }
}