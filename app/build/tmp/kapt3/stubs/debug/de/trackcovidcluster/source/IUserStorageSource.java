package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\n\u0010\b\u001a\u0004\u0018\u00010\u0007H&J\n\u0010\t\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\n\u001a\u00020\u000bH&J&\u0010\f\u001a\u0004\u0018\u00010\u00072\u001a\u0010\r\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000ej\n\u0012\u0006\u0012\u0004\u0018\u00010\u0007`\u000fH&\u00a8\u0006\u0010"}, d2 = {"Lde/trackcovidcluster/source/IUserStorageSource;", "", "createUser", "", "createUserKeys", "getUUIDsFromServerOvr", "getUUIDsFromUser", "", "getUserPublicKey", "getUserUUID", "isUserExisting", "", "sendClusterSubmission", "arrayList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "app_debug"})
public abstract interface IUserStorageSource {
    
    public abstract boolean isUserExisting();
    
    public abstract void createUser();
    
    public abstract void createUserKeys();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserPublicKey();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUUIDsFromUser();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserUUID();
    
    public abstract void getUUIDsFromServerOvr();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String sendClusterSubmission(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> arrayList);
}