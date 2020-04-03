package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Suppress(names = {"NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"})
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u0000 >2\u00020\u00012\u00020\u0002:\u0001>B\u0005\u00a2\u0006\u0002\u0010\u0003J \u0010&\u001a\u00020\'2\u0016\u0010(\u001a\u0012\u0012\u0004\u0012\u00020*0)j\b\u0012\u0004\u0012\u00020*`+H\u0003J\b\u0010,\u001a\u00020\'H\u0016J\u0012\u0010-\u001a\u00020\'2\b\u0010.\u001a\u0004\u0018\u00010/H\u0014J\b\u00100\u001a\u00020\'H\u0014J\u0012\u00101\u001a\u00020\'2\b\u00102\u001a\u0004\u0018\u000103H\u0014J\b\u00104\u001a\u00020\'H\u0014J\b\u00105\u001a\u00020\'H\u0014J$\u00106\u001a\u00020\'2\b\u00107\u001a\u0004\u0018\u00010\b2\b\u00108\u001a\u0004\u0018\u00010\b2\u0006\u00109\u001a\u00020*H\u0002J\b\u0010:\u001a\u00020\'H\u0003J\u0010\u0010;\u001a\u00020\'2\u0006\u0010<\u001a\u00020*H\u0002J\b\u0010=\u001a\u00020\'H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R.\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u0001`\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\u000b\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u0001`\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\f\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u0001`\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u00020\u001f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lde/trackcovidcluster/status/StatusActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/altbeacon/beacon/BeaconConsumer;", "()V", "beaconParser", "Lorg/altbeacon/beacon/BeaconParser;", "contacts", "Ljava/util/HashMap;", "", "Lorg/altbeacon/beacon/Beacon;", "Lkotlin/collections/HashMap;", "contactsDistance", "contactsUUIDs", "mBackgroudPowerSaver", "Lorg/altbeacon/beacon/powersave/BackgroundPowerSaver;", "mBeaconManager", "Lorg/altbeacon/beacon/BeaconManager;", "mCurrentStatusImage", "Landroid/widget/ImageView;", "mCurrentStatusText", "Landroid/widget/TextView;", "mReceiver", "Landroid/content/BroadcastReceiver;", "mReportBottomText", "mReportTopText", "mShouldCreatePayload", "", "mStatusTextView", "mViewModel", "Lde/trackcovidcluster/status/StatusViewModel;", "mViewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "getMViewModelFactory", "()Landroidx/lifecycle/ViewModelProvider$Factory;", "setMViewModelFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "uuids", "Lorg/json/JSONObject;", "createPayload", "", "contacs", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "onBeaconServiceConnect", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "Landroid/content/Intent;", "onPause", "onResume", "setBeaconTransmitter", "major", "minor", "counter", "startAdvertising", "updateStatus", "status", "verifyBluetooth", "Companion", "app_debug"})
public class StatusActivity extends androidx.appcompat.app.AppCompatActivity implements org.altbeacon.beacon.BeaconConsumer {
    private org.json.JSONObject uuids;
    private java.util.HashMap<java.lang.String, org.altbeacon.beacon.Beacon> contacts;
    private java.util.HashMap<java.lang.String, java.lang.String> contactsDistance;
    private java.util.HashMap<java.lang.String, java.lang.String> contactsUUIDs;
    private android.content.BroadcastReceiver mReceiver;
    private boolean mShouldCreatePayload;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public androidx.lifecycle.ViewModelProvider.Factory mViewModelFactory;
    private de.trackcovidcluster.status.StatusViewModel mViewModel;
    private android.widget.ImageView mCurrentStatusImage;
    private android.widget.TextView mCurrentStatusText;
    private android.widget.TextView mStatusTextView;
    private org.altbeacon.beacon.BeaconManager mBeaconManager;
    private org.altbeacon.beacon.powersave.BackgroundPowerSaver mBackgroudPowerSaver;
    private org.altbeacon.beacon.BeaconParser beaconParser;
    private android.widget.TextView mReportBottomText;
    private android.widget.TextView mReportTopText;
    private static final int DEFAULT = -1;
    public static final de.trackcovidcluster.status.StatusActivity.Companion Companion = null;
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
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    /**
     * BLE Functions for scanning and creating the encrypted payload
     */
    @java.lang.Override()
    public void onBeaconServiceConnect() {
    }
    
    private final void verifyBluetooth() {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    private final void createPayload(java.util.ArrayList<java.lang.Integer> contacs) {
    }
    
    /**
     * Functions for setting the beacons to Advertise themselves
     */
    private final void setBeaconTransmitter(java.lang.String major, java.lang.String minor, int counter) {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    private final void startAdvertising() {
    }
    
    /**
     * Update the users State
     */
    private final void updateStatus(int status) {
    }
    
    public StatusActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lde/trackcovidcluster/status/StatusActivity$Companion;", "", "()V", "DEFAULT", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}