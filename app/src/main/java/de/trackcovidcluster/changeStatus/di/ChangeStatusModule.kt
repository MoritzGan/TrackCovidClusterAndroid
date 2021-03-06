package de.trackcovidcluster.status.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.trackcovidcluster.di.ViewModelKey
import de.trackcovidcluster.changeStatus.ChangeStatusViewModel


@Module
abstract class ChangeStatusModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChangeStatusViewModel::class)
    abstract fun bindViewModel(mainViewModel: ChangeStatusViewModel): ViewModel
}
