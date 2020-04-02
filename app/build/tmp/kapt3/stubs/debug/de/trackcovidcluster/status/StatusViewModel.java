package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nJ\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u0017\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lde/trackcovidcluster/status/StatusViewModel;", "Landroidx/lifecycle/ViewModel;", "mStatusStorageSource", "Lde/trackcovidcluster/source/IStatusStorageSource;", "mUserStorageSource", "Lde/trackcovidcluster/source/IUserStorageSource;", "(Lde/trackcovidcluster/source/IStatusStorageSource;Lde/trackcovidcluster/source/IUserStorageSource;)V", "getBeacon", "Lorg/altbeacon/beacon/Beacon;", "uuid", "", "major", "minor", "getPublicKeyByteArray", "", "getServerPubKey", "getStatus", "", "getStatusFromSource", "", "getUUIDs", "Lorg/json/JSONObject;", "getUserUUID", "setMaybeInfected", "Companion", "app_debug"})
public final class StatusViewModel extends androidx.lifecycle.ViewModel {
    private final de.trackcovidcluster.source.IStatusStorageSource mStatusStorageSource = null;
    private final de.trackcovidcluster.source.IUserStorageSource mUserStorageSource = null;
    private static final java.lang.String GET_STATUS_TAG = "GET_STATUS";
    public static final de.trackcovidcluster.status.StatusViewModel.Companion Companion = null;
    
    public final void getStatus() {
    }
    
    public final int getStatusFromSource() {
        return 0;
    }
    
    public final void setMaybeInfected() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final org.altbeacon.beacon.Beacon getBeacon(@org.jetbrains.annotations.NotNull()
    java.lang.String uuid, @org.jetbrains.annotations.NotNull()
    java.lang.String major, @org.jetbrains.annotations.NotNull()
    java.lang.String minor) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserUUID() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getPublicKeyByteArray() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject getUUIDs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getServerPubKey() {
        return null;
    }
    
    @javax.inject.Inject()
    public StatusViewModel(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.IStatusStorageSource mStatusStorageSource, @org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.IUserStorageSource mUserStorageSource) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lde/trackcovidcluster/status/StatusViewModel$Companion;", "", "()V", "GET_STATUS_TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}