package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 @2\u00020\u00012\u00020\u0002:\u0001@B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0003J\b\u0010.\u001a\u00020+H\u0017J\b\u0010/\u001a\u00020+H\u0017J\u0012\u00100\u001a\u00020+2\b\u00101\u001a\u0004\u0018\u000102H\u0015J\b\u00103\u001a\u00020+H\u0015J\u0012\u00104\u001a\u00020+2\b\u00105\u001a\u0004\u0018\u000106H\u0014J\b\u00107\u001a\u00020+H\u0015J\b\u00108\u001a\u00020+H\u0015J\u0010\u00109\u001a\u00020+2\u0006\u0010:\u001a\u00020\nH\u0002J\b\u0010;\u001a\u00020+H\u0003J\b\u0010<\u001a\u00020+H\u0003J\u0010\u0010=\u001a\u00020+2\u0006\u0010>\u001a\u00020?H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R.\u0010\b\u001a\"\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\tj\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u0001`\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\r\u001a\"\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\u000e\u001a\"\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u00020#8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lde/trackcovidcluster/status/StatusActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/altbeacon/beacon/BeaconConsumer;", "()V", "beaconParser", "Lorg/altbeacon/beacon/BeaconParser;", "beaconTransmitter", "Lorg/altbeacon/beacon/BeaconTransmitter;", "contacts", "Ljava/util/HashMap;", "", "Lorg/altbeacon/beacon/Beacon;", "Lkotlin/collections/HashMap;", "contactsDistance", "contactsUUIDs", "isAdvertising", "", "mBackgroundPowerSaver", "Lorg/altbeacon/beacon/powersave/BackgroundPowerSaver;", "mBeaconManager", "Lorg/altbeacon/beacon/BeaconManager;", "mCurrentStatusImage", "Landroid/widget/ImageView;", "mCurrentStatusText", "Landroid/widget/TextView;", "mPositiveButton", "Landroid/widget/Button;", "mReceiver", "Landroid/content/BroadcastReceiver;", "mReportBottomText", "mReportTopText", "mStatusTextView", "mViewModel", "Lde/trackcovidcluster/status/StatusViewModel;", "mViewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "getMViewModelFactory", "()Landroidx/lifecycle/ViewModelProvider$Factory;", "setMViewModelFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "uuids", "Lorg/json/JSONObject;", "createPayload", "", "hashedUUIDReceived", "", "onBackPressed", "onBeaconServiceConnect", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "Landroid/content/Intent;", "onPause", "onResume", "setBeaconTransmitter", "uuid", "startAdvertising", "startBeaconService", "updateStatus", "status", "", "Companion", "app_debug"})
public class StatusActivity extends androidx.appcompat.app.AppCompatActivity implements org.altbeacon.beacon.BeaconConsumer {
    private org.json.JSONObject uuids;
    private java.util.HashMap<java.lang.String, org.altbeacon.beacon.Beacon> contacts;
    private java.util.HashMap<java.lang.String, java.lang.String> contactsDistance;
    private java.util.HashMap<java.lang.String, java.lang.String> contactsUUIDs;
    private android.content.BroadcastReceiver mReceiver;
    private boolean isAdvertising = false;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public androidx.lifecycle.ViewModelProvider.Factory mViewModelFactory;
    private de.trackcovidcluster.status.StatusViewModel mViewModel;
    private android.widget.ImageView mCurrentStatusImage;
    private android.widget.TextView mCurrentStatusText;
    private android.widget.TextView mStatusTextView;
    private org.altbeacon.beacon.BeaconManager mBeaconManager;
    private org.altbeacon.beacon.powersave.BackgroundPowerSaver mBackgroundPowerSaver;
    private org.altbeacon.beacon.BeaconParser beaconParser;
    private android.widget.TextView mReportBottomText;
    private android.widget.TextView mReportTopText;
    private android.widget.Button mPositiveButton;
    private org.altbeacon.beacon.BeaconTransmitter beaconTransmitter;
    private static final java.lang.String SCANNER_TAG = "Scanning";
    private static final java.lang.String ADVERTISING_TAG = "Advertising";
    public static final de.trackcovidcluster.status.StatusActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.ViewModelProvider.Factory getMViewModelFactory() {
        return null;
    }
    
    public final void setMViewModelFactory(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.ViewModelProvider.Factory p0) {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    @kotlin.ExperimentalStdlibApi()
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    @kotlin.ExperimentalStdlibApi()
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override()
    protected void onResume() {
    }
    
    @kotlin.ExperimentalStdlibApi()
    @kotlin.ExperimentalUnsignedTypes()
    @java.lang.Override()
    protected void onPause() {
    }
    
    @kotlin.ExperimentalStdlibApi()
    @kotlin.ExperimentalUnsignedTypes()
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.ExperimentalStdlibApi()
    @kotlin.ExperimentalUnsignedTypes()
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    /**
     * BLE Functions for scanning and creating the encrypted payload
     */
    @kotlin.ExperimentalUnsignedTypes()
    @kotlin.ExperimentalStdlibApi()
    @java.lang.Override()
    public void onBeaconServiceConnect() {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    @kotlin.ExperimentalStdlibApi()
    private final void createPayload(byte[] hashedUUIDReceived) {
    }
    
    /**
     * Functions for setting the beacons to Advertise themselves
     */
    private final void setBeaconTransmitter(java.lang.String uuid) {
    }
    
    @kotlin.ExperimentalUnsignedTypes()
    @kotlin.ExperimentalStdlibApi()
    private final void startAdvertising() {
    }
    
    /**
     * Update the users State
     */
    private final void updateStatus(int status) {
    }
    
    @kotlin.ExperimentalStdlibApi()
    @kotlin.ExperimentalUnsignedTypes()
    private final void startBeaconService() {
    }
    
    public StatusActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lde/trackcovidcluster/status/StatusActivity$Companion;", "", "()V", "ADVERTISING_TAG", "", "SCANNER_TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}