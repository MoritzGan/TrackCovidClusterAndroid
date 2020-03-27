package de.trackcovidcluster

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.android.*
import de.trackcovidcluster.di.RxWorkerFactory
import javax.inject.Inject

class TrackCovidClusterApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

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
