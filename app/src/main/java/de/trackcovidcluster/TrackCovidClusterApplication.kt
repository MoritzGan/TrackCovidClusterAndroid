package de.trackcovidcluster

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.android.*
import de.trackcovidcluster.di.RxWorkerFactory
import javax.inject.Inject

class TrackCovidClusterApplication : Application(), HasAndroidInjector{

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    lateinit var mRxWorkerFactory: RxWorkerFactory

    override fun onCreate() {
        super.onCreate()
        DaggerTrackCovidClusterComponent.builder()
            .app(this)
            .build()
            .inject(this)

        // WorkManager Init
        WorkManager.initialize(
            this,
            Configuration.Builder().run {
                setWorkerFactory(mRxWorkerFactory)
                build()
            })
    }
}
