package com.tuccro.curex.di

import androidx.work.WorkManager
import com.tuccro.curex.ui.activity.main.MainActivityViewModel
import com.tuccro.curex.ui.screens.WalletViewModel
import com.tuccro.domain.usecase.GetAllBalancesUseCase
import com.tuccro.domain.usecase.GetCurrencyRatesFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideWalletViewModel(
        getCurrencyRatesFlowUseCase: GetCurrencyRatesFlowUseCase,
        getAllBalancesUseCase: GetAllBalancesUseCase
    ): WalletViewModel {
        return WalletViewModel(getCurrencyRatesFlowUseCase, getAllBalancesUseCase)
    }

    @Provides
    @Singleton
    fun provideMainActivityViewModel(workManager: WorkManager): MainActivityViewModel {
        return MainActivityViewModel(workManager)
    }
}