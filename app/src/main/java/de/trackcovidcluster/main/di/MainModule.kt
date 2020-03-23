package de.trackcovidcluster.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.trackcovidcluster.di.ViewModelFactory
import de.trackcovidcluster.di.ViewModelKey
import de.trackcovidcluster.main.MainActivityViewModel


@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    // Bind your View Model here
    abstract fun bindMainViewModel(mainViewModel: MainActivityViewModel): ViewModel
}
