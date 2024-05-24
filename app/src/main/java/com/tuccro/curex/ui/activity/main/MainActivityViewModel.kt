package com.tuccro.curex.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.tuccro.curex.worker.UpdateRatesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    fun startPeriodicUpdate() {
        viewModelScope.launch {
            val updateRatesRequest = OneTimeWorkRequestBuilder<UpdateRatesWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .addTag(UpdateRatesWorker.NAME)
                .build()

            workManager.enqueueUniqueWork(
                UpdateRatesWorker.NAME,
                ExistingWorkPolicy.REPLACE,
                updateRatesRequest
            )
        }
    }

    fun stopPeriodicUpdate() {
        viewModelScope.launch {
            workManager.cancelUniqueWork(UpdateRatesWorker.NAME)
        }
    }
}
