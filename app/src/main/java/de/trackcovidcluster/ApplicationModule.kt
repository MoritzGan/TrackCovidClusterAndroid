package de.trackcovidcluster

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.trackcovidcluster.di.ViewModelFactory

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }
}
