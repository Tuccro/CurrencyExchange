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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuccro.curex.R
import com.tuccro.curex.ui.screens.dialog.ShowExchangeResultDialog
import com.tuccro.curex.ui.theme.BackgroundDark
import com.tuccro.curex.ui.theme.ButtonShape
import com.tuccro.curex.ui.theme.PrimaryLight
import com.tuccro.domain.model.Balance
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    val viewModel: WalletViewModel = hiltViewModel()
    val balanceList by viewModel.combinedList.collectAsState()

    val allCurrencies = balanceList.map { it.currency }
    val sellCurrency by viewModel.sellCurrency.collectAsState()
    val sellSum by viewModel.sellSum.collectAsState()

    val receiveCurrency by viewModel.receiveCurrency.collectAsState()
    val receiveSum by viewModel.receiveSum.collectAsState()

    val exchangeResult by viewModel.exchangeResult.collectAsState()

    exchangeResult?.let {
        ShowExchangeResultDialog(it.first, it.second) { viewModel.closeAlert() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.currency_converter),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = PrimaryLight)
        )
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

        val buttonEnabled by viewModel.submitButtonActive

        TextButton(
            onClick = { viewModel.submit() },
            shape = ButtonShape,
            enabled = buttonEnabled,
            colors = ButtonColors(
                containerColor = PrimaryLight,
                contentColor = Color.White,
                disabledContentColor = Color.Gray,
                disabledContainerColor = BackgroundDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }
}

@Composable
fun BalancesDisplay(balances: List<Balance>) {
    Column {
        Text(
            text = stringResource(id = R.string.my_balances),
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
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
                text = String.format(Locale.ROOT, "%.2f", balance.amount),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
