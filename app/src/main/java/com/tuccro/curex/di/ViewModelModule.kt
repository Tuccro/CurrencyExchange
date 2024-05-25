package com.tuccro.curex.di

import androidx.work.WorkManager
import com.tuccro.curex.ui.activity.main.MainActivityViewModel
import com.tuccro.curex.ui.screens.splash.SplashViewModel
import com.tuccro.curex.ui.screens.wallet.WalletViewModel
import com.tuccro.domain.usecase.CalculateExchangeUseCase
import com.tuccro.domain.usecase.GetAllBalancesUseCase
import com.tuccro.domain.usecase.GetCurrencyRatesFlowUseCase
import com.tuccro.domain.usecase.ProcessExchangeUseCase
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
        getAllBalancesUseCase: GetAllBalancesUseCase,
        calculateExchangeUseCase: CalculateExchangeUseCase,
        processExchangeUseCase: ProcessExchangeUseCase
    ): WalletViewModel {
        return WalletViewModel(
            getCurrencyRatesFlowUseCase,
            getAllBalancesUseCase,
            calculateExchangeUseCase,
            processExchangeUseCase
        )
    }

    @Provides
    @Singleton
    fun provideSplashViewModel(
        getCurrencyRatesFlowUseCase: GetCurrencyRatesFlowUseCase
    ): SplashViewModel {
        return SplashViewModel(
            getCurrencyRatesFlowUseCase
        )
    }

    @Provides
    @Singleton
    fun provideMainActivityViewModel(workManager: WorkManager): MainActivityViewModel {
        return MainActivityViewModel(workManager)
    }
}