package de.trackcovidcluster.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class RxWorkerFactory @Inject constructor(
    private val workersFactories:
    Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<IChildRxWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry = workersFactories.entries.find {
            Class.forName(workerClassName).isAssignableFrom(it.key)
        }

        return if (foundEntry == null) {
            val workerClass =
                Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)
            val constructor = workerClass.getDeclaredConstructor(
                Context::class.java,
                WorkerParameters::class.java
            )
            constructor.newInstance(appContext, workerParameters)
        } else {
            foundEntry.value.get().create(
                context = appContext,
                workerParameters = workerParameters
            )
        }
    }
}
