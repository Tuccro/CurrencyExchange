package com.tuccro.data.di

import com.tuccro.data.repository.ExchangeRepositoryImpl
import com.tuccro.data.repository.SettingsRepositoryImpl
import com.tuccro.data.repository.WalletRepositoryImpl
import com.tuccro.domain.repository.ExchangeRepository
import com.tuccro.domain.repository.SettingsRepository
import com.tuccro.domain.repository.WalletRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWalletRepository(impl: WalletRepositoryImpl): WalletRepository

    @Binds
    @Singleton
    abstract fun bindExchangeRepository(impl: ExchangeRepositoryImpl): ExchangeRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}