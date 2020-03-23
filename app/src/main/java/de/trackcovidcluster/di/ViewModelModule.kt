package de.trackcovidcluster.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.trackcovidcluster.di.ViewModelFactory
import de.trackcovidcluster.di.ViewModelKey
import de.trackcovidcluster.status.ChangeStatusViewModel
import javax.inject.Named


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory

}
