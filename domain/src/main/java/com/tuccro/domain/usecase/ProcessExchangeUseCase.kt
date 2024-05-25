package com.tuccro.domain.usecase

import com.tuccro.domain.model.exchange.ExchangeRequest
import com.tuccro.domain.model.exchange.ExchangeResponse
import com.tuccro.domain.repository.ExchangeRepository
import com.tuccro.domain.usecase.base.BaseUseCase
import javax.inject.Inject

class ProcessExchangeUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) : BaseUseCase<ExchangeRequest, ExchangeResponse>() {

    override suspend fun invoke(params: ExchangeRequest): ExchangeResponse {
        return exchangeRepository.exchange(params)
    }
}
