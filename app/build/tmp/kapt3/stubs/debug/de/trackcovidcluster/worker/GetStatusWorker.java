package de.trackcovidcluster.worker;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0011B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\nH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lde/trackcovidcluster/worker/GetStatusWorker;", "Landroidx/work/RxWorker;", "mContext", "Landroid/content/Context;", "mWorkerParams", "Landroidx/work/WorkerParameters;", "mUserStorageSource", "Lde/trackcovidcluster/source/IUserStorageSource;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lde/trackcovidcluster/source/IUserStorageSource;)V", "createNotificationChannel", "", "createWork", "Lio/reactivex/Single;", "Landroidx/work/ListenableWorker$Result;", "isForeground", "", "sendPushNotification", "Factory", "app_debug"})
public final class GetStatusWorker extends androidx.work.RxWorker {
    private final de.trackcovidcluster.source.IUserStorageSource mUserStorageSource = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Single<androidx.work.ListenableWorker.Result> createWork() {
        return null;
    }
    
    private final void sendPushNotification() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final boolean isForeground() {
        return false;
    }
    
    @javax.inject.Inject()
    public GetStatusWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters mWorkerParams, @org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.IUserStorageSource mUserStorageSource) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lde/trackcovidcluster/worker/GetStatusWorker$Factory;", "Lde/trackcovidcluster/di/IChildRxWorkerFactory;", "mUserStorageSource", "Lde/trackcovidcluster/source/IUserStorageSource;", "(Lde/trackcovidcluster/source/IUserStorageSource;)V", "create", "Landroidx/work/RxWorker;", "context", "Landroid/content/Context;", "workerParameters", "Landroidx/work/WorkerParameters;", "app_debug"})
    public static final class Factory implements de.trackcovidcluster.di.IChildRxWorkerFactory {
        private final de.trackcovidcluster.source.IUserStorageSource mUserStorageSource = null;
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public androidx.work.RxWorker create(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.work.WorkerParameters workerParameters) {
            return null;
        }
        
        @javax.inject.Inject()
        public Factory(@org.jetbrains.annotations.NotNull()
        de.trackcovidcluster.source.IUserStorageSource mUserStorageSource) {
            super();
        }
    }
}