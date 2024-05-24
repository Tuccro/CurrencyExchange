package com.tuccro.data.di

import android.util.Log
import com.tuccro.data.api.CurrencyRatesApi
import com.tuccro.data.network.RatesNetworkStoreImpl
import com.tuccro.domain.store.RatesNetworkStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIME_OUT = 6000

    @Provides
    @Singleton
    fun provideJsonConfig(): Json = Json {
        explicitNulls = false
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient = HttpClient(Android) {

        install(ContentNegotiation) {

            json(json)

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    @Provides
    @Singleton
    fun provideApiService(httpClient: HttpClient): CurrencyRatesApi {
        return CurrencyRatesApi(httpClient)
    }

    @Provides
    @Singleton
    fun provideRatesNetworkStore(
        currencyRatesApi: CurrencyRatesApi
    ): RatesNetworkStore {
        return RatesNetworkStoreImpl(currencyRatesApi)
    }
}