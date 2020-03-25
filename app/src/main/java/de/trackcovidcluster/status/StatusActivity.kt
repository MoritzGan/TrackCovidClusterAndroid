package de.trackcovidcluster.status

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import de.trackcovidcluster.R
import de.trackcovidcluster.bluetooth.ScanForMobiles


class StatusActivity : AppCompatActivity() {

    private val handler = Handler();
    private val REQUEST_ENABLE_BT = 1;

    fun withDelay(delay : Long, block : () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        /**
         * Ask User to enable Bluetooth
         */

        val mBluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            // TODO Log error message
        }

        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        /**
         * TODO Implement scan for devices near
         */
        val scanForMobiles = ScanForMobiles(this);

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {
                scanForMobiles.startScan();
                ha.postDelayed(this, 10000);
            }
        }, 10000)
    }
}
