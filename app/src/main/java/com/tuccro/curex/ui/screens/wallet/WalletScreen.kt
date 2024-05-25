package com.tuccro.curex.ui.screens.wallet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuccro.domain.model.Balance

@Composable
fun WalletScreen() {
    val viewModel: WalletViewModel = hiltViewModel()
    val balanceList by viewModel.combinedList.collectAsState()

    val allCurrencies = balanceList.map { it.currency }
    val sellCurrency by viewModel.sellCurrency.collectAsState()
    val sellSum by viewModel.sellSum.collectAsState()

    val receiveCurrency by viewModel.receiveCurrency.collectAsState()
    val receiveSum by viewModel.receiveSum.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        BalancesDisplay(balanceList)

        CurrencyExchangeView(
            currencies = allCurrencies,
            sellCurrency = sellCurrency,
            sellSum = sellSum,
            receiveCurrency = receiveCurrency,
            receiveSum = receiveSum,
            selectSellCurrency = { viewModel.setSellCurrency(it) },
            selectReceiveCurrency = { viewModel.setReceiveCurrency(it) },
            setSellSum = { viewModel.setSellSum(it) }
        )
    }
}

@Composable
fun BalancesDisplay(balances: List<Balance>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(balances) { balance ->
            BalanceItem(balance)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BalanceItem(balance: Balance) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.size(120.dp, 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = balance.currency, style = MaterialTheme.typography.titleMedium)
            Text(
                text = String.format("%.2f", balance.amount),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
