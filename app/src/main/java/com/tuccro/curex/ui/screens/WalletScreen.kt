package com.tuccro.curex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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

    BalancesDisplay(balanceList)
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
            Text(text = String.format("%.2f", balance.amount), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
