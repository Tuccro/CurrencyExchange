package com.tuccro.data.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.tuccro.domain.store.SettingsStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsStoreImpl @Inject constructor(context: Context) : SettingsStore {

    companion object {
        private const val PREFS_NAME = "settings_prefs"
        private const val EXCHANGES_COUNT_KEY = "exchanges_count"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("NewApi")
    override fun increaseExchangesCount() {
        val currentCount = getExchangesCount()
        sharedPreferences.edit().putInt(EXCHANGES_COUNT_KEY, currentCount + 1).apply()
    }

    override fun getExchangesCount(): Int {
        return sharedPreferences.getInt(EXCHANGES_COUNT_KEY, 0)
    }

    override fun getFirstPaidExchangeNumber(): Int {
        // according to a task, the class can be extended by some server-side settings
        return 6
    }

    override fun getExchangeFeePercent(): Double {
        // according to a task, the class can be extended by some server-side settings
        return 0.7
    }
}