package de.trackcovidcluster

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import de.trackcovidcluster.server.ServerAdapter

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context =
        application.applicationContext


    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)

    @Provides
    fun provideServerAdapter(): ServerAdapter = ServerAdapter()

}
