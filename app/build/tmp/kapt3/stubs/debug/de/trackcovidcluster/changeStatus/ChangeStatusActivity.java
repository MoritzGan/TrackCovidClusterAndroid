package de.trackcovidcluster.changeStatus;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lde/trackcovidcluster/changeStatus/ChangeStatusActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mViewModel", "Lde/trackcovidcluster/changeStatus/ChangeStatusViewModel;", "mViewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "getMViewModelFactory", "()Landroidx/lifecycle/ViewModelProvider$Factory;", "setMViewModelFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "getNextStatus", "", "status", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class ChangeStatusActivity extends androidx.appcompat.app.AppCompatActivity {
    private de.trackcovidcluster.changeStatus.ChangeStatusViewModel mViewModel;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public androidx.lifecycle.ViewModelProvider.Factory mViewModelFactory;
    private static final int DEFAULT = -1;
    public static final de.trackcovidcluster.changeStatus.ChangeStatusActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.ViewModelProvider.Factory getMViewModelFactory() {
        return null;
    }
    
    public final void setMViewModelFactory(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.ViewModelProvider.Factory p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void getNextStatus(int status) {
    }
    
    public ChangeStatusActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lde/trackcovidcluster/changeStatus/ChangeStatusActivity$Companion;", "", "()V", "DEFAULT", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}