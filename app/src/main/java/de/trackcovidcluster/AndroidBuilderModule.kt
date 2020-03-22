package de.trackcovidcluster

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.trackcovidcluster.main.MainActivity

@Module
abstract class AndroidBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
