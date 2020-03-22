package de.trackcovidcluster.main.di


import dagger.Component
import de.trackcovidcluster.main.MainActivityViewModel

@Component( modules = arrayOf(
    MainModule::class
))
interface ViewModelComponent {
    // inject your view models
    fun inject( mainViewModel: MainActivityViewModel )

}
