package com.tuccro.data.repository

import com.tuccro.data.db.dao.WalletDao
import com.tuccro.data.db.mapper.toDomainModel
import com.tuccro.data.db.mapper.toEntityModel
import com.tuccro.domain.model.Balance
import com.tuccro.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepositoryImpl @Inject constructor(
    private val walletDao: WalletDao
) : WalletRepository {

    override suspend fun insertBalance(balance: Balance) {
        walletDao.insertBalance(balance.toEntityModel())
    }

    override fun getAllBalances(): Flow<List<Balance>> {
        return walletDao.getAllBalances().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}