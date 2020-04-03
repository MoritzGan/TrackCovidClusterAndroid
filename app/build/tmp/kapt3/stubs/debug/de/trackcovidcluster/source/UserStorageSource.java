package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\n\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lde/trackcovidcluster/source/UserStorageSource;", "Lde/trackcovidcluster/source/IUserStorageSource;", "mSharedPreferences", "Landroid/content/SharedPreferences;", "(Landroid/content/SharedPreferences;)V", "generateAndSaveKeyPair", "", "getBase64UserPublicKey", "", "getServerPublicKey", "getUUIDsFromSharedPreferences", "isUserExisting", "", "setPublicKey", "publicKey", "setUUIDsFromServer", "uuidsJson", "Lorg/json/JSONObject;", "Companion", "app_debug"})
public final class UserStorageSource implements de.trackcovidcluster.source.IUserStorageSource {
    private final android.content.SharedPreferences mSharedPreferences = null;
    private static final java.lang.String SERVER_PUBLIC_KEY = "SERVER_PUBLIC_KEY";
    private static final java.lang.String UUID_LIST = "UUIDS";
    private static final java.lang.String USER_PUBLIC_KEY_ID = "PUBLIC_KEY";
    private static final java.lang.String USER_PRIVATE_KEY_ID = "USER_PRIVATE_KEY_ID";
    public static final de.trackcovidcluster.source.UserStorageSource.Companion Companion = null;
    
    @java.lang.Override()
    public boolean isUserExisting() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getBase64UserPublicKey() {
        return null;
    }
    
    @java.lang.Override()
    public void setUUIDsFromServer(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject uuidsJson) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getUUIDsFromSharedPreferences() {
        return null;
    }
    
    @java.lang.Override()
    public void generateAndSaveKeyPair() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getServerPublicKey() {
        return null;
    }
    
    @java.lang.Override()
    public void setPublicKey(@org.jetbrains.annotations.NotNull()
    java.lang.String publicKey) {
    }
    
    @javax.inject.Inject()
    public UserStorageSource(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences mSharedPreferences) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lde/trackcovidcluster/source/UserStorageSource$Companion;", "", "()V", "SERVER_PUBLIC_KEY", "", "USER_PRIVATE_KEY_ID", "USER_PUBLIC_KEY_ID", "UUID_LIST", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}