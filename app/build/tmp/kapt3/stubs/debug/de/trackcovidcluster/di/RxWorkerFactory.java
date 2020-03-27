package de.trackcovidcluster.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B.\b\u0007\u0012%\u0010\u0002\u001a!\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u000f\u0012\r\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0002\b\b0\u0003\u00a2\u0006\u0002\u0010\tJ\"\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R-\u0010\u0002\u001a!\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u000f\u0012\r\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0002\b\b0\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lde/trackcovidcluster/di/RxWorkerFactory;", "Landroidx/work/WorkerFactory;", "workersFactories", "", "Ljava/lang/Class;", "Landroidx/work/ListenableWorker;", "Ljavax/inject/Provider;", "Lde/trackcovidcluster/di/IChildRxWorkerFactory;", "Lkotlin/jvm/JvmSuppressWildcards;", "(Ljava/util/Map;)V", "createWorker", "appContext", "Landroid/content/Context;", "workerClassName", "", "workerParameters", "Landroidx/work/WorkerParameters;", "app_debug"})
public final class RxWorkerFactory extends androidx.work.WorkerFactory {
    private final java.util.Map<java.lang.Class<? extends androidx.work.ListenableWorker>, javax.inject.Provider<de.trackcovidcluster.di.IChildRxWorkerFactory>> workersFactories = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.work.ListenableWorker createWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context appContext, @org.jetbrains.annotations.NotNull()
    java.lang.String workerClassName, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParameters) {
        return null;
    }
    
    @javax.inject.Inject()
    public RxWorkerFactory(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Class<? extends androidx.work.ListenableWorker>, javax.inject.Provider<de.trackcovidcluster.di.IChildRxWorkerFactory>> workersFactories) {
        super();
    }
}