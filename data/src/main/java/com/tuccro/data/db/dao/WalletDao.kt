package com.tuccro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuccro.data.db.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balanceEntity: BalanceEntity)

    @Query("SELECT * FROM balance_table")
    fun getAllBalances(): Flow<List<BalanceEntity>>
}