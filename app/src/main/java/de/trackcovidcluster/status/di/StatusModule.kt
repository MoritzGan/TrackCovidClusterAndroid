package de.trackcovidcluster.status.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.trackcovidcluster.di.ViewModelKey
import de.trackcovidcluster.status.StatusViewModel


@Module
abstract class StatusModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatusViewModel::class)
    abstract fun bindViewModel(mainViewModel: StatusViewModel): ViewModel

}
