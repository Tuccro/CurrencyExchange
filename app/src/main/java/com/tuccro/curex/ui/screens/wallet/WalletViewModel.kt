package com.tuccro.curex.ui.screens.wallet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuccro.domain.model.Balance
import com.tuccro.domain.model.CurrencyRates
import com.tuccro.domain.model.exchange.ExchangeRequest
import com.tuccro.domain.model.exchange.ExchangeResponse
import com.tuccro.domain.usecase.CalculateExchangeUseCase
import com.tuccro.domain.usecase.GetAllBalancesUseCase
import com.tuccro.domain.usecase.GetCurrencyRatesFlowUseCase
import com.tuccro.domain.usecase.ProcessExchangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getCurrencyRatesFlowUseCase: GetCurrencyRatesFlowUseCase,
    private val getAllBalancesUseCase: GetAllBalancesUseCase,
    private val calculateExchangeUseCase: CalculateExchangeUseCase,
    private val processExchangeUseCase: ProcessExchangeUseCase
) : ViewModel() {

    private val _currencyRates = MutableStateFlow<CurrencyRates?>(null)
    val currencyRates: StateFlow<CurrencyRates?> = _currencyRates

    private val _balances = MutableStateFlow<List<Balance>>(emptyList())
    val balances: StateFlow<List<Balance>> = _balances

    private val _combinedList = MutableStateFlow<List<Balance>>(emptyList())
    val combinedList: StateFlow<List<Balance>> = _combinedList

    private val _sellCurrency = MutableStateFlow(DEFAULT_SELL_CURRENCY)
    val sellCurrency: StateFlow<String> = _sellCurrency

    private val _sellSum = MutableStateFlow(DEFAULT_SELL_SUM)
    val sellSum: StateFlow<Double> = _sellSum

    private val _receiveCurrency = MutableStateFlow(DEFAULT_RECEIVE_CURRENCY)
    val receiveCurrency: StateFlow<String> = _receiveCurrency

    private val _receiveSum = MutableStateFlow(DEFAULT_SELL_SUM)
    val receiveSum: StateFlow<Double> = _receiveSum

    private val _exchangeResult =
        MutableStateFlow<Pair<ExchangeRequest, ExchangeResponse>?>(null)
    val exchangeResult: StateFlow<Pair<ExchangeRequest, ExchangeResponse>?> =
        _exchangeResult

    private val _submitButtonActive = mutableStateOf(true)
    val submitButtonActive: State<Boolean> = _submitButtonActive

    private var initialized = false

    init {
        observeCurrencyRates()
        observeBalances()
        combineFlows()
    }

    fun submit() {
        _submitButtonActive.value = false

        viewModelScope.launch {

            val currencyFrom = sellCurrency.value
            val currencyTo = receiveCurrency.value

            val request = ExchangeRequest(
                balanceFrom = balances.value.firstOrNull { it.currency == currencyFrom }?.amount
                    ?: 0.0,
                balanceTo = balances.value.firstOrNull { it.currency == currencyTo }?.amount
                    ?: 0.0,
                currencyFrom = currencyFrom,
                sum = sellSum.value,
                currencyTo = currencyTo,
                currencyRates = currencyRates.value!!
            )

            val result = processExchangeUseCase.invoke(request)

            _exchangeResult.value = request to result

        }.invokeOnCompletion { _submitButtonActive.value = true }
    }

    fun closeAlert() {
        _exchangeResult.value = null
    }

    fun setSellCurrency(currency: String) {
        _sellCurrency.value = currency
        updateReceiveSum()
    }

    fun setReceiveCurrency(currency: String) {
        _receiveCurrency.value = currency
        updateReceiveSum()
    }

    fun setSellSum(sum: Double) {
        _sellSum.value = sum
        updateReceiveSum()
    }

    private fun initializeExchange() {
        if (!initialized) {
            viewModelScope.launch {
                val currencies = currencyRates.value?.rates?.keys!!

                _sellCurrency.value =
                    if (currencies.contains(DEFAULT_SELL_CURRENCY)) DEFAULT_SELL_CURRENCY
                    else currencies.first()
                _sellSum.value = 100.0

                _receiveCurrency.value =
                    if (currencies.contains(DEFAULT_RECEIVE_CURRENCY)) DEFAULT_RECEIVE_CURRENCY
                    else currencies.filterNot { it == sellCurrency.value }.first()

                updateReceiveSum()
                initialized = true
            }
        }
    }

    private fun updateReceiveSum() {
        viewModelScope.launch {
            val currencyFrom = sellCurrency.value
            val currencyTo = receiveCurrency.value

            val calculateExchangeResult = calculateExchangeUseCase.invoke(
                ExchangeRequest(
                    balanceFrom = balances.value.firstOrNull { it.currency == currencyFrom }?.amount
                        ?: 0.0,
                    balanceTo = balances.value.firstOrNull { it.currency == currencyTo }?.amount
                        ?: 0.0,
                    currencyFrom = currencyFrom,
                    sum = sellSum.value,
                    currencyTo = currencyTo,
                    currencyRates = currencyRates.value!!
                )
            )

            _receiveSum.value = calculateExchangeResult.resultSum
        }
    }

    private fun observeCurrencyRates() {
        viewModelScope.launch {
            getCurrencyRatesFlowUseCase(Unit).collect { rates ->
                _currencyRates.value = rates
                initializeExchange()
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

    companion object {
        const val DEFAULT_SELL_CURRENCY = "EUR"
        const val DEFAULT_SELL_SUM = 100.0
        const val DEFAULT_RECEIVE_CURRENCY = "USD"
    }
}
