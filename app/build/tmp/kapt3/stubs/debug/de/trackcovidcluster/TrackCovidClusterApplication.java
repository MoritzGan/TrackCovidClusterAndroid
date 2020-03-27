package de.trackcovidcluster;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R$\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0014"}, d2 = {"Lde/trackcovidcluster/TrackCovidClusterApplication;", "Landroid/app/Application;", "Ldagger/android/HasActivityInjector;", "()V", "activityInjector", "Ldagger/android/DispatchingAndroidInjector;", "Landroid/app/Activity;", "getActivityInjector", "()Ldagger/android/DispatchingAndroidInjector;", "setActivityInjector", "(Ldagger/android/DispatchingAndroidInjector;)V", "mRxWorkerFactory", "Lde/trackcovidcluster/di/RxWorkerFactory;", "getMRxWorkerFactory", "()Lde/trackcovidcluster/di/RxWorkerFactory;", "setMRxWorkerFactory", "(Lde/trackcovidcluster/di/RxWorkerFactory;)V", "Ldagger/android/AndroidInjector;", "onCreate", "", "app_debug"})
public final class TrackCovidClusterApplication extends android.app.Application implements dagger.android.HasActivityInjector {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dagger.android.DispatchingAndroidInjector<android.app.Activity> activityInjector;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public de.trackcovidcluster.di.RxWorkerFactory mRxWorkerFactory;
    
    @org.jetbrains.annotations.NotNull()
    public final dagger.android.DispatchingAndroidInjector<android.app.Activity> getActivityInjector() {
        return null;
    }
    
    public final void setActivityInjector(@org.jetbrains.annotations.NotNull()
    dagger.android.DispatchingAndroidInjector<android.app.Activity> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public dagger.android.AndroidInjector<android.app.Activity> activityInjector() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final de.trackcovidcluster.di.RxWorkerFactory getMRxWorkerFactory() {
        return null;
    }
    
    public final void setMRxWorkerFactory(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.di.RxWorkerFactory p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public TrackCovidClusterApplication() {
        super();
    }
}