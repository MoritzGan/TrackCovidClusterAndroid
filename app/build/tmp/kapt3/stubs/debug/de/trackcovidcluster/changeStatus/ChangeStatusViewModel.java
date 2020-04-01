package de.trackcovidcluster.changeStatus;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u00020\b2\u001a\u0010\t\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nj\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b`\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lde/trackcovidcluster/changeStatus/ChangeStatusViewModel;", "Landroidx/lifecycle/ViewModel;", "mUserStorageSource", "Lde/trackcovidcluster/source/UserStorageSource;", "mStatusStorageSource", "Lde/trackcovidcluster/source/StatusStorageSource;", "(Lde/trackcovidcluster/source/UserStorageSource;Lde/trackcovidcluster/source/StatusStorageSource;)V", "sendStatus", "", "listOfEncounters", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "app_debug"})
public final class ChangeStatusViewModel extends androidx.lifecycle.ViewModel {
    private final de.trackcovidcluster.source.UserStorageSource mUserStorageSource = null;
    private final de.trackcovidcluster.source.StatusStorageSource mStatusStorageSource = null;
    
    public final void sendStatus(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> listOfEncounters) {
    }
    
    @javax.inject.Inject()
    public ChangeStatusViewModel(@org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.UserStorageSource mUserStorageSource, @org.jetbrains.annotations.NotNull()
    de.trackcovidcluster.source.StatusStorageSource mStatusStorageSource) {
        super();
    }
}