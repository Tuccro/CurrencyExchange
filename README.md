# CurrencyExchange
Android test task

## Shortened description:


The user has a multi-currency account with a starting balance of **1000 Euros (EUR)**. He can convert any currency to any if the API provides the rate but the balance can't fall below zero. Create **an input** where the user will enter an amount, a **picker** for currency being **sold**, and a **picker** for currency being **bought**.

For example, the user inputs **100.00**, and picks **Euros** to sell and **Dollars** to buy. The user then clicks the **Submit** button, a message is shown `You have converted 100.00 EUR to 110.30 USD` and now the balance is **900.00 Euros** and **110.30 US Dollars**.

Also, there may be a commission fee for the currency exchange operation. The first five currency exchanges are free of charge but afterwards, they're charged 0.7% of the currency being traded. The commission fee should be displayed in the message that appears after the conversion. For example:

```
You have converted 100.00 EUR to 110.00 USD. Commission Fee - 0.70 EUR.
```

The commission fee should be deducted from each currency balance separately.

Currency exchange rates should be synchronized **every 5 seconds**


## Used technologies and frameworks:
* At first, it's an Android app
* Kotlin as a main programming language
* Hilt as a DI
* Jetpack Compose as UI
* Ktor for network requests
* Clean Architecture and MVVM
* Kotlin Coroutines and Flows
* Jetpack Navigation, ViewModels, Worker API
