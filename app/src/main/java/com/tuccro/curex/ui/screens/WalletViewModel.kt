package com.tuccro.curex.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuccro.domain.model.Balance
import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.usecase.GetAllBalancesUseCase
import com.tuccro.domain.usecase.GetCurrencyRatesFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getCurrencyRatesFlowUseCase: GetCurrencyRatesFlowUseCase,
    private val getAllBalancesUseCase: GetAllBalancesUseCase
) : ViewModel() {

    private val _currencyRates = MutableStateFlow<CurrencyRates?>(null)
    val currencyRates: StateFlow<CurrencyRates?> = _currencyRates

    private val _balances = MutableStateFlow<List<Balance>>(emptyList())
    val balances: StateFlow<List<Balance>> = _balances

    private val _combinedList = MutableStateFlow<List<Balance>>(emptyList())
    val combinedList: StateFlow<List<Balance>> = _combinedList

    init {
        observeCurrencyRates()
        observeBalances()
        combineFlows()
    }

    private fun observeCurrencyRates() {
        viewModelScope.launch {
            getCurrencyRatesFlowUseCase(Unit).collect { rates ->
                _currencyRates.value = rates
            }
        }
    }

    private fun observeBalances() {
        viewModelScope.launch {
            getAllBalancesUseCase(Unit).collect { balances ->
                _balances.value = balances
            }
        }
    }

    private fun combineFlows() {
        viewModelScope.launch {
            combine(_balances, _currencyRates) { balances, currencyRates ->
                val nonZeroBalances = balances.filter { it.amount > 0 }
                val zeroBalances = currencyRates?.rates?.keys
                    ?.filter { currency -> balances.none { it.currency == currency } }
                    ?.map { currency -> Balance(currency, 0.0) }
                    ?: emptyList()
                nonZeroBalances + zeroBalances
            }.collect { combinedList ->
                _combinedList.value = combinedList
            }
        }
    }
}
