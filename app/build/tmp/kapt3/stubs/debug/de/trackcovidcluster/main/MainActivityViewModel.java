package de.trackcovidcluster.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\b\u0010\b\u001a\u00020\u0006H\u0002J\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lde/trackcovidcluster/main/MainActivityViewModel;", "Landroidx/lifecycle/ViewModel;", "mUserStorageSource", "Lde/trackcovidcluster/source/UserStorageSource;", "(Lde/trackcovidcluster/source/UserStorageSource;)V", "createUser", "", "generateKeyPair", "getPublicKey", "getUUIDsFromServer", "isFirstTimeUser", "", "app_debug"})
public final class MainActivityViewModel extends androidx.lifecycle.ViewModel {
    private final de.trackcovidcluster.source.UserStorageSource mUserStorageSource = null;
    
    public final boolean isFirstTimeUser() {
        return false;
    }
    
    public final void createUser() {
    }
    
    public final void generateKeyPair() {
    }
    
    public final void getUUIDsFromServer() {
    }
    
    private final void getPublicKey() {
    }
    
    @javax.inject.Inject()
    public MainActivityViewModel(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.UserStorageSource mUserStorageSource) {
        super();
    }
}