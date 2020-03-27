package de.trackcovidcluster.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface IChildRxWorkerFactory {
    fun create(context: Context, workerParameters: WorkerParameters): ListenableWorker
}
