package com.tuccro.domain.usecase

import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.store.RatesNetworkStore
import com.tuccro.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrencyRatesFlowUseCase @Inject constructor(
    private val ratesNetworkStore: RatesNetworkStore
) : BaseUseCase<Unit, StateFlow<CurrencyRates?>>() {

    override suspend fun invoke(params: Unit): StateFlow<CurrencyRates?> {
        return ratesNetworkStore.getCurrencyRatesFlow()
    }
}
