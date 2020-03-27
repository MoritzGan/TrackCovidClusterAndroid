package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lde/trackcovidcluster/status/Constants;", "", "()V", "INFECTED", "", "MAYBE_INFECTED", "NOT_INFECTED", "PUSH_NOTIFICATION_CHANNEL", "", "STATUS_API_KEY", "STATUS_KEY", "app_debug"})
public final class Constants {
    public static final int NOT_INFECTED = 0;
    public static final int MAYBE_INFECTED = 1;
    public static final int INFECTED = 2;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String STATUS_KEY = "status";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String STATUS_API_KEY = "statusFromApi";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PUSH_NOTIFICATION_CHANNEL = "2019";
    public static final de.trackcovidcluster.status.Constants INSTANCE = null;
    
    private Constants() {
        super();
    }
}