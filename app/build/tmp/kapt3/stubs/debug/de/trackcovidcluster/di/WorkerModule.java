package de.trackcovidcluster.di;

import java.lang.System;

@kotlin.Suppress(names = {"unused"})
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b!\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H!\u00a2\u0006\u0002\b\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\'\u00a8\u0006\u0010"}, d2 = {"Lde/trackcovidcluster/di/WorkerModule;", "", "()V", "bindStatusStorageSource", "Lde/trackcovidcluster/source/IStatusStorageSource;", "statusStorageSource", "Lde/trackcovidcluster/source/StatusStorageSource;", "bindStatusStorageSource$app_debug", "bindUserStorageSource", "Lde/trackcovidcluster/source/IUserStorageSource;", "userStorageSource", "Lde/trackcovidcluster/source/UserStorageSource;", "bindWorkerFactory", "Lde/trackcovidcluster/di/IChildRxWorkerFactory;", "getStatusWorker", "Lde/trackcovidcluster/worker/GetStatusWorker$Factory;", "app_debug"})
@dagger.Module()
public abstract class WorkerModule {
    
    @org.jetbrains.annotations.NotNull()
    @RxWorkerKey(value = de.trackcovidcluster.worker.GetStatusWorker.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract de.trackcovidcluster.di.IChildRxWorkerFactory bindWorkerFactory(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.worker.GetStatusWorker.Factory getStatusWorker);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract de.trackcovidcluster.source.IUserStorageSource bindUserStorageSource(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.UserStorageSource userStorageSource);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract de.trackcovidcluster.source.IStatusStorageSource bindStatusStorageSource$app_debug(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.StatusStorageSource statusStorageSource);
    
    public WorkerModule() {
        super();
    }
}