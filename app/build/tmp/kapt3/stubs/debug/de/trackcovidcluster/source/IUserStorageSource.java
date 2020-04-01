package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0006H&J\n\u0010\b\u001a\u0004\u0018\u00010\u0006H&J\b\u0010\t\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\u00032\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\rH&\u00a8\u0006\u000e"}, d2 = {"Lde/trackcovidcluster/source/IUserStorageSource;", "", "createUserKeys", "", "getUUIDsFromServerOvr", "getUUIDsFromUser", "", "getUserPublicKey", "getUserUUID", "isUserExisting", "", "sendClusterSubmission", "list", "", "app_debug"})
public abstract interface IUserStorageSource {
    
    public abstract boolean isUserExisting();
    
    public abstract void createUserKeys();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserPublicKey();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUUIDsFromUser();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserUUID();
    
    public abstract void getUUIDsFromServerOvr();
    
    public abstract void sendClusterSubmission(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> list);
}