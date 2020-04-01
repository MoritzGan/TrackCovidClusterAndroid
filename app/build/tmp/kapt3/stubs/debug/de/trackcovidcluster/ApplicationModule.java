package de.trackcovidcluster;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\n"}, d2 = {"Lde/trackcovidcluster/ApplicationModule;", "", "()V", "provideContext", "Landroid/content/Context;", "application", "Landroid/app/Application;", "provideSharedPreferences", "Landroid/content/SharedPreferences;", "context", "app_debug"})
@dagger.Module()
public final class ApplicationModule {
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.Context provideContext(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.SharedPreferences provideSharedPreferences(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public ApplicationModule() {
        super();
    }
}