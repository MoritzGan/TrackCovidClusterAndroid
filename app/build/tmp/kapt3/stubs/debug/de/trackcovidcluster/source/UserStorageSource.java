package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lde/trackcovidcluster/source/UserStorageSource;", "Lde/trackcovidcluster/source/IUserStorageSource;", "mSharedPreferences", "Landroid/content/SharedPreferences;", "mServerAdapter", "Lde/trackcovidcluster/server/ServerAdapter;", "(Landroid/content/SharedPreferences;Lde/trackcovidcluster/server/ServerAdapter;)V", "createUser", "", "getUUID", "", "isUserExisting", "", "Companion", "app_debug"})
public final class UserStorageSource implements de.trackcovidcluster.source.IUserStorageSource {
    private final android.content.SharedPreferences mSharedPreferences = null;
    private final de.trackcovidcluster.server.ServerAdapter mServerAdapter = null;
    private static final java.lang.String USER_ID = "USER_ID";
    private static final java.lang.String PK_ID = "PUBLIC_KEY";
    public static final de.trackcovidcluster.source.UserStorageSource.Companion Companion = null;
    
    @java.lang.Override()
    public boolean isUserExisting() {
        return false;
    }
    
    @java.lang.Override()
    public void createUser() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getUUID() {
        return null;
    }
    
    @javax.inject.Inject()
    public UserStorageSource(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences mSharedPreferences, @org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.server.ServerAdapter mServerAdapter) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lde/trackcovidcluster/source/UserStorageSource$Companion;", "", "()V", "PK_ID", "", "USER_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}