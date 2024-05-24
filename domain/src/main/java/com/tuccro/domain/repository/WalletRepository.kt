package com.tuccro.domain.repository;

import com.tuccro.domain.model.Balance
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun insertBalance(balance: Balance)
    fun getAllBalances(): Flow<List<Balance>>
}