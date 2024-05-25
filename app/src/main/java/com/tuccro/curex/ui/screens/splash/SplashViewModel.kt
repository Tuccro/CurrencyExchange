package com.tuccro.curex.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuccro.domain.usecase.GetCurrencyRatesFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrencyRatesFlowUseCase: GetCurrencyRatesFlowUseCase
) : ViewModel() {

    var loaded: () -> Unit = {}

    fun observeCurrencyRates() {
        viewModelScope.launch {
            getCurrencyRatesFlowUseCase(Unit).collect { currencyRates ->
                if (currencyRates != null) {
                    loaded()
                }
            }
        }
    }
}
