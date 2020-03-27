package de.trackcovidcluster;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lde/trackcovidcluster/TrackCovidClusterComponent;", "", "inject", "", "app", "Lde/trackcovidcluster/TrackCovidClusterApplication;", "Builder", "app_debug"})
@dagger.Component(modules = {dagger.android.AndroidInjectionModule.class, de.trackcovidcluster.AndroidBuilderModule.class, de.trackcovidcluster.ApplicationModule.class, de.trackcovidcluster.di.ViewModelModule.class, de.trackcovidcluster.main.di.MainModule.class, de.trackcovidcluster.status.di.StatusModule.class, de.trackcovidcluster.status.di.ChangeStatusModule.class, de.trackcovidcluster.di.WorkerModule.class})
@javax.inject.Singleton()
public abstract interface TrackCovidClusterComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.TrackCovidClusterApplication app);
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0003H\'J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lde/trackcovidcluster/TrackCovidClusterComponent$Builder;", "", "app", "Landroid/app/Application;", "build", "Lde/trackcovidcluster/TrackCovidClusterComponent;", "app_debug"})
    @dagger.Component.Builder()
    public static abstract interface Builder {
        
        @org.jetbrains.annotations.NotNull()
        @dagger.BindsInstance()
        public abstract de.trackcovidcluster.TrackCovidClusterComponent.Builder app(@org.jetbrains.annotations.NotNull()
        android.app.Application app);
        
        @org.jetbrains.annotations.NotNull()
        public abstract de.trackcovidcluster.TrackCovidClusterComponent build();
    }
}