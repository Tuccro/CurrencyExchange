package com.tuccro.domain.usecase;

import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.store.RatesNetworkStore
import com.tuccro.domain.usecase.base.BaseUseCase
import com.tuccro.domain.utils.Result
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(private val ratesNetworkStore: RatesNetworkStore) :
    BaseUseCase<Unit, Result<CurrencyRates>>() {
    override suspend operator fun invoke(params: Unit): Result<CurrencyRates> {
        return ratesNetworkStore.getRates()
    }
}