package com.tuccro.curex.ui.screens.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.tuccro.curex.R
import com.tuccro.domain.model.exchange.ExchangeRequest
import com.tuccro.domain.model.exchange.ExchangeResponse
import com.tuccro.domain.model.exchange.Status
import java.util.Locale

@Composable
fun ShowExchangeResultDialog(
    exchangeRequest: ExchangeRequest,
    exchangeResponse: ExchangeResponse,
    onDismiss: () -> Unit
) {
    val message = when (exchangeResponse.status) {
        Status.OK -> {
            val from = String.format(
                Locale.ROOT,
                "%.2f %s",
                exchangeRequest.sum,
                exchangeRequest.currencyFrom
            )
            val to = String.format(
                Locale.ROOT,
                "%.2f %s",
                exchangeResponse.resultSum,
                exchangeRequest.currencyTo
            )
            val firstPart = stringResource(id = R.string.exchange_success, from, to)

            val secondPart = if (exchangeResponse.fee > 0) {
                val fee = String.format(
                    Locale.ROOT,
                    "%.2f %s",
                    exchangeResponse.fee,
                    exchangeRequest.currencyFrom
                )
                stringResource(id = R.string.exchange_fee, fee)
            } else ""
            "$firstPart $secondPart"
        }

        Status.NOT_ENOUGH_MONEY -> stringResource(
            id = R.string.exchange_error,
            exchangeRequest.currencyFrom
        )
    }

    MessageAlertDialog(message = message) {
        onDismiss()
    }
}

@Composable
fun MessageAlertDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        properties = DialogProperties()
    )
}