package de.trackcovidcluster

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.trackcovidcluster.main.MainActivity
import de.trackcovidcluster.changeStatus.ChangeStatusActivity
import de.trackcovidcluster.status.StatusActivity

@Module
abstract class AndroidBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeChangeStatusActivity(): ChangeStatusActivity

    @ContributesAndroidInjector
    abstract fun contributeStatusActivity(): StatusActivity
}
