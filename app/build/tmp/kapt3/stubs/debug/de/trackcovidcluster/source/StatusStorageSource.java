package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lde/trackcovidcluster/source/StatusStorageSource;", "Lde/trackcovidcluster/source/IStatusStorageSource;", "mSharedPreferences", "Landroid/content/SharedPreferences;", "(Landroid/content/SharedPreferences;)V", "getStatus", "", "setMaybeInfectedStatus", "", "setStatus", "status", "Companion", "app_debug"})
public final class StatusStorageSource implements de.trackcovidcluster.source.IStatusStorageSource {
    private final android.content.SharedPreferences mSharedPreferences = null;
    private static final java.lang.String STATUS = "STATUS";
    public static final de.trackcovidcluster.source.StatusStorageSource.Companion Companion = null;
    
    @java.lang.Override()
    public void setStatus(int status) {
    }
    
    @java.lang.Override()
    public void setMaybeInfectedStatus() {
    }
    
    @java.lang.Override()
    public int getStatus() {
        return 0;
    }
    
    @javax.inject.Inject()
    public StatusStorageSource(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences mSharedPreferences) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lde/trackcovidcluster/source/StatusStorageSource$Companion;", "", "()V", "STATUS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}