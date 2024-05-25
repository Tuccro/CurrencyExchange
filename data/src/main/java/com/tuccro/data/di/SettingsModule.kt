package com.tuccro.data.di

import android.content.Context
import com.tuccro.data.settings.SettingsStoreImpl
import com.tuccro.domain.store.SettingsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun bindSettingsStore(@ApplicationContext context: Context): SettingsStore {
        return SettingsStoreImpl(context)
    }
}