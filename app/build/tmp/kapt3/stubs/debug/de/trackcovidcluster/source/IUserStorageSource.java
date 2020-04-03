package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0005H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u000f"}, d2 = {"Lde/trackcovidcluster/source/IUserStorageSource;", "", "createUserKeys", "", "getUUIDsFromUser", "", "getUserPublicKey", "getUserUUID", "isUserExisting", "", "setPublicKey", "publicKey", "setUUIDsFromServer", "uuidsJson", "Lorg/json/JSONObject;", "app_debug"})
public abstract interface IUserStorageSource {
    
    public abstract boolean isUserExisting();
    
    public abstract void createUserKeys();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserPublicKey();
    
    public abstract void setPublicKey(@org.jetbrains.annotations.NotNull()
    java.lang.String publicKey);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUUIDsFromUser();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getUserUUID();
    
    public abstract void setUUIDsFromServer(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject uuidsJson);
}