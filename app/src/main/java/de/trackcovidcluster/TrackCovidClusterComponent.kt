package de.trackcovidcluster

import dagger.Component
import dagger.android.AndroidInjectionModule
import de.trackcovidcluster.main.di.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    MainModule::class
))
interface TrackCovidClusterComponent {
    fun inject(app: TrackCovidClusterApplication)
}
