package de.trackcovidcluster.status;

import java.lang.System;

@kotlin.Suppress(names = {"NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"})
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\b\u0016\u0018\u0000 ;2\u00020\u00012\u00020\u0002:\u0001;B\u0005\u00a2\u0006\u0002\u0010\u0003J,\u0010%\u001a\u00020&2\"\u0010\'\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`\tH\u0002J\b\u0010(\u001a\u00020&H\u0016J\u0012\u0010)\u001a\u00020&2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\b\u0010,\u001a\u00020&H\u0014J\b\u0010-\u001a\u00020&H\u0014J\b\u0010.\u001a\u00020&H\u0014J)\u0010/\u001a\u00020&2\b\u00100\u001a\u0004\u0018\u0001012\b\u00102\u001a\u0004\u0018\u0001012\u0006\u00103\u001a\u000201H\u0002\u00a2\u0006\u0002\u00104J\u001f\u00105\u001a\u00020&2\b\u00100\u001a\u0004\u0018\u0001012\u0006\u00103\u001a\u000201H\u0002\u00a2\u0006\u0002\u00106J\b\u00107\u001a\u00020&H\u0002J\u0010\u00108\u001a\u00020&2\u0006\u00109\u001a\u000201H\u0002J\b\u0010:\u001a\u00020&H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R.\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u0001`\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\n\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u0001`\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\u000b\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u0001`\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u00020\u001e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lde/trackcovidcluster/status/StatusActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/altbeacon/beacon/BeaconConsumer;", "()V", "beaconParser", "Lorg/altbeacon/beacon/BeaconParser;", "contacts", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "contactsDistance", "contactsUUIDs", "mBackgroudPowerSaver", "Lorg/altbeacon/beacon/powersave/BackgroundPowerSaver;", "mBeaconManager", "Lorg/altbeacon/beacon/BeaconManager;", "mCurrentStatusImage", "Landroid/widget/ImageView;", "mCurrentStatusText", "Landroid/widget/TextView;", "mReceiver", "Landroid/content/BroadcastReceiver;", "mSharedPreferences", "Landroid/content/SharedPreferences;", "mShouldCreatePayload", "", "mStatusTextView", "mViewModel", "Lde/trackcovidcluster/status/StatusViewModel;", "mViewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "getMViewModelFactory", "()Landroidx/lifecycle/ViewModelProvider$Factory;", "setMViewModelFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "uuids", "Lorg/json/JSONObject;", "createPayload", "", "contacs", "onBeaconServiceConnect", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "setBeaconTransmitter", "major", "", "minor", "counter", "(Ljava/lang/Integer;Ljava/lang/Integer;I)V", "setBeaconTransmitterLast", "(Ljava/lang/Integer;I)V", "startAdvertising", "updateStatus", "status", "verifyBluetooth", "Companion", "app_debug"})
public class StatusActivity extends androidx.appcompat.app.AppCompatActivity implements org.altbeacon.beacon.BeaconConsumer {
    private org.json.JSONObject uuids;
    private java.util.HashMap<java.lang.String, java.lang.String> contacts;
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
    private android.content.SharedPreferences mSharedPreferences;
    private org.altbeacon.beacon.BeaconParser beaconParser;
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
    
    private final void createPayload(java.util.HashMap<java.lang.String, java.lang.String> contacs) {
    }
    
    /**
     * Functions for setting the beacons to Advertise themselves
     */
    private final void setBeaconTransmitter(java.lang.Integer major, java.lang.Integer minor, int counter) {
    }
    
    private final void setBeaconTransmitterLast(java.lang.Integer major, int counter) {
    }
    
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