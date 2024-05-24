package com.tuccro.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance_table")
data class BalanceEntity(
    @PrimaryKey val currency: String,
    val amount: Double
)