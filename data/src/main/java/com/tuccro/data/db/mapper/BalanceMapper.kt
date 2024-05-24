package com.tuccro.data.db.mapper

import com.tuccro.data.db.entity.BalanceEntity
import com.tuccro.domain.model.Balance

fun BalanceEntity.toDomainModel(): Balance {
    return Balance(currency = this.currency, amount = this.amount)
}

fun Balance.toEntityModel(): BalanceEntity {
    return BalanceEntity(currency = this.currency, amount = this.amount)
}