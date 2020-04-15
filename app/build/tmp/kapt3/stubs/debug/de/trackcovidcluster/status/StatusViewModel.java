package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\bH\u0007J\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lde/trackcovidcluster/status/StatusViewModel;", "Landroidx/lifecycle/ViewModel;", "mStatusStorageSource", "Lde/trackcovidcluster/source/IStatusStorageSource;", "mUserStorageSource", "Lde/trackcovidcluster/source/IUserStorageSource;", "(Lde/trackcovidcluster/source/IStatusStorageSource;Lde/trackcovidcluster/source/IUserStorageSource;)V", "doSha3", "", "message", "digest", "Lorg/bouncycastle/crypto/digests/SHA3Digest;", "getPublicKeyByteArray", "getServerPubKey", "", "getStatus", "", "getStatusFromSource", "", "getUUIDs", "Lorg/json/JSONObject;", "setMaybeInfected", "app_debug"})
public final class StatusViewModel extends androidx.lifecycle.ViewModel {
    private final de.trackcovidcluster.source.IStatusStorageSource mStatusStorageSource = null;
    private final de.trackcovidcluster.source.IUserStorageSource mUserStorageSource = null;
    
    public final void getStatus() {
    }
    
    public final int getStatusFromSource() {
        return 0;
    }
    
    public final void setMaybeInfected() {
    }
    
    /**
     * Returns the actual public key of the user as a ByteArray.
     */
    @org.jetbrains.annotations.NotNull()
    @kotlin.ExperimentalStdlibApi()
    public final byte[] getPublicKeyByteArray() {
        return null;
    }
    
    private final byte[] doSha3(byte[] message, org.bouncycastle.crypto.digests.SHA3Digest digest) {
        return null;
    }
    
    /**
     * Returns the public key of the server from the userStorageSource.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getServerPubKey() {
        return null;
    }
    
    /**
     * TODO Check if needed anymore
     */
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject getUUIDs() {
        return null;
    }
    
    @javax.inject.Inject()
    public StatusViewModel(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.IStatusStorageSource mStatusStorageSource, @org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.IUserStorageSource mUserStorageSource) {
        super();
    }
}