package com.tuccro.domain.usecase

import com.tuccro.domain.model.Balance
import com.tuccro.domain.repository.WalletRepository
import com.tuccro.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBalancesUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) : BaseUseCase<Unit, Flow<List<Balance>>>() {
    override suspend fun invoke(params: Unit): Flow<List<Balance>> {
        return walletRepository.getAllBalances()
    }
}
