package com.tuccro.curex.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.tuccro.domain.usecase.GetRatesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class UpdateRatesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getRatesUseCase: GetRatesUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            when (getRatesUseCase(Unit)) {
                is com.tuccro.domain.utils.Result.Success -> {
                    scheduleNextUpdate()
                    Result.success()
                }

                is com.tuccro.domain.utils.Result.Error -> {
                    Result.retry()
                }
            }
        }
    }

    private fun scheduleNextUpdate() {
        val workManager = WorkManager.getInstance(applicationContext)
        val updateRatesRequest = OneTimeWorkRequestBuilder<UpdateRatesWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniqueWork(
            UpdateRatesWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            updateRatesRequest
        )
    }

    companion object {
        const val NAME = "UpdateRatesWork"
    }
}
