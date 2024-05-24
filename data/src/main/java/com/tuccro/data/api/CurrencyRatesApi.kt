package com.tuccro.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class CurrencyRatesApi @Inject constructor(private val client: HttpClient) {

    private val BASE_URL = "https://developers.paysera.com"
    private val API = "/tasks/api/currency-exchange-rates"

    suspend fun getRates(): HttpResponse =
        client.get("$BASE_URL$API")

}
