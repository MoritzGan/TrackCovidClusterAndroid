package de.trackcovidcluster

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import de.trackcovidcluster.di.ViewModelModule
import de.trackcovidcluster.main.di.MainModule
import de.trackcovidcluster.status.di.StatusModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        AndroidBuilderModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        MainModule::class,
        StatusModule::class
    )
)
interface TrackCovidClusterComponent {
    fun inject(app: TrackCovidClusterApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): TrackCovidClusterComponent
    }
}

