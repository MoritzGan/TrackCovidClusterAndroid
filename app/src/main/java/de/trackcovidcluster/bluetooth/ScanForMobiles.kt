package de.trackcovidcluster.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*

class ScanForMobiles(activity: Activity) {

    private val bluetoothManager: BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter
    private val activity: Activity

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val deviceName = device.name
                val deviceHardwareAddress = device.address

                val rssi = intent.getShortExtra(
                    BluetoothDevice.EXTRA_RSSI,
                    Short.MIN_VALUE
                ).toInt()
                Log.d("", " $rssi")
                Log.d("", " $deviceName")

                if (rssi > -60 && deviceName != null) {
                    // Do sth here. Devices here are near enough
                }
            }
        }
    }

    fun startScan() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(receiver, filter) // onFound receiver is called
        bluetoothAdapter.startDiscovery()
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Throws(JSONException::class)
    fun createCookie(userid: String): JSONObject {
        val cookie = JSONObject()
        val data = userid.toByteArray(StandardCharsets.UTF_8)
        val encrData =
            Base64.encodeToString(data, Base64.DEFAULT)
        val timestamp = Date().time.toString()
        cookie.put("UUID", encrData)
        cookie.put("Timestamp", timestamp)
        return cookie
    }

    init {
        bluetoothManager =
            activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        this.activity = activity
    }
}