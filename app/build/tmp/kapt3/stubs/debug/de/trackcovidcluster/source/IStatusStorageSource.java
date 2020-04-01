package de.trackcovidcluster.source;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0006H&J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0003H&\u00a8\u0006\u000b"}, d2 = {"Lde/trackcovidcluster/source/IStatusStorageSource;", "", "getContactTime", "", "getStatus", "setContactTime", "", "time", "setMaybeInfectedStatus", "setStatus", "status", "app_debug"})
public abstract interface IStatusStorageSource {
    
    public abstract void setStatus(int status);
    
    public abstract void setMaybeInfectedStatus();
    
    public abstract int getStatus();
    
    public abstract void setContactTime(int time);
    
    public abstract int getContactTime();
}