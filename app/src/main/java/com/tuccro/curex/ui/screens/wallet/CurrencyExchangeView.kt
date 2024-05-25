package com.tuccro.curex.ui.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuccro.CurEx.R
import java.util.Locale

@Composable
fun CurrencyExchangeView(
    currencies: List<String>,
    sellCurrency: String,
    sellSum: Double,
    receiveCurrency: String,
    receiveSum: Double,
    selectSellCurrency: (String) -> Unit,
    selectReceiveCurrency: (String) -> Unit,
    setSellSum: (Double) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.currency_exchange),
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ExchangeRow(
            label = stringResource(id = R.string.sell),
            amount = sellSum,
            setAmount = setSellSum,
            selectedCurrency = sellCurrency,
            onCurrencySelected = { selectSellCurrency(it) },
            currencies = currencies,
            icon = R.drawable.ic_arrow_up,
            iconColor = Color.Red,
            textColor = Color.Black,
            isEditable = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExchangeRow(
            label = stringResource(id = R.string.receive),
            amount = receiveSum,
            setAmount = {},
            selectedCurrency = receiveCurrency,
            onCurrencySelected = { selectReceiveCurrency(it) },
            currencies = currencies,
            icon = R.drawable.ic_arrow_down,
            iconColor = Color.Green,
            textColor = Color.Green,
            isEditable = false
        )
    }
}

@Composable
fun ExchangeRow(
    label: String,
    amount: Double,
    setAmount: (Double) -> Unit,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    currencies: List<String>,
    icon: Int,
    iconColor: Color,
    textColor: Color,
    isEditable: Boolean
) {
    var amountState by remember { mutableDoubleStateOf(amount) }
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor, shape = CircleShape)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isEditable) {
                BasicTextField(
//                    value = String.format(Locale.ROOT, "%.2f", amountState),
                    value = amountState.toString(),
                    onValueChange = {
                        if (it.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                            amountState = it.toDouble()
                            setAmount(amountState)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.Transparent)
                )
            } else {
                Text(
                    text = String.format(Locale.ROOT, "+%.2f", amount),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.width(100.dp)
                )
            }

            Box {
                Text(
                    text = selectedCurrency,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = textColor
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            onClick = {
                                onCurrencySelected(currency)
                                expanded = false
                            }, text = {
                                Text(text = currency)
                            })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyExchangeScreenPreview() {
    CurrencyExchangeView(
        currencies = listOf("EUR", "USD", "GBP"),
        "EUR",
        100.0,
        "USD",
        110.3,
        {},
        {},
        {})
}
