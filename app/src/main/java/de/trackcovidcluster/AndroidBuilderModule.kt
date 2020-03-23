package de.trackcovidcluster

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.trackcovidcluster.main.MainActivity
import de.trackcovidcluster.status.ChangeStatusActivity

@Module
abstract class AndroidBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeChangeStatusActivity(): ChangeStatusActivity
}
