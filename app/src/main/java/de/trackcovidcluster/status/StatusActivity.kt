package de.trackcovidcluster.status

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.*
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.changeStatus.ChangeStatusActivity
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.status.Constants.INFECTED
import de.trackcovidcluster.status.Constants.MAYBE_INFECTED
import de.trackcovidcluster.status.Constants.NOT_INFECTED
import de.trackcovidcluster.status.Constants.STATUS_API_KEY
import de.trackcovidcluster.status.Constants.STATUS_KEY
import de.trackcovidcluster.tracking.BackgroundLocationService
import de.trackcovidcluster.tracking.BackgroundLocationService.LocationServiceBinder
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import java.util.*
import javax.inject.Inject


class StatusActivity : AppCompatActivity(), ServiceConnection, BeaconConsumer {

    companion object {
        private const val DEFAULT = -1
    }

    // region members
    private lateinit var mViewModel: StatusViewModel
    private var beaconManager: BeaconManager? = null
    private val PERMISSIONS_REQUEST = 100
    private lateinit var mUserStorageSource: IUserStorageSource
    var gpsService: BackgroundLocationService? = null
    var mTracking = false

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private var mReceiver: BroadcastReceiver? = null

    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(StatusViewModel::class.java)

        mViewModel.getStatus()

        mCurrentStatusImage = currentStatusImage
        mCurrentStatusText = currentStatusText

        val status = intent.getIntExtra(STATUS_KEY, DEFAULT)
        if (status != DEFAULT) {
            updateStatus(status = status)
        } else {
            val currentStatus = mViewModel.getStatusFromSource()
            updateStatus(status = currentStatus)
        }

        // region Get Permissions for Tracking
        val lm =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish()
        }

        val permission = ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        )

        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Start Tracking here
        } else {

            ActivityCompat.requestPermissions(
                this, arrayOf(ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
        }
        // endregion

        /**
         * Stream as BLE Beacon
         */

        var beacon: Beacon? = Beacon.Builder()
            .setId1(mUserStorageSource.getUUID()) // hashed
            .setId2("1")
            .setId3("2")
            .setManufacturer(0x004c)
            .setTxPower(-59)
            .build()

        val beaconParser: BeaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")

        val beaconTransmitter =
            BeaconTransmitter(applicationContext, beaconParser)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
                override fun onStartFailure(errorCode: Int) {
                    Log.e(
                        "BeaconState: ",
                        " \n Advertisement start failed with code: $errorCode"
                    )
                }

                override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                    Log.i("BeaconState: ",
                        "Advertisement start succeeded.")
                }
            })
        }

        /**
         * Listen for near Devices
         */
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        beaconManager!!.startMonitoringBeaconsInRegion(Region("myBeacons", Identifier.parse("12345678-1234-5678-1234-567812345678"),
                                                                    Identifier.parse("1"), Identifier.parse("1")))
        /**
         * TODO: This should change on Server poll :
         */
        maybeInfectedContainer.setOnClickListener {
            startActivity(
                Intent(this, ChangeStatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        MAYBE_INFECTED
                    )
            )
        }

        infectedContainer.setOnClickListener {
            startActivity(
                Intent(this, ChangeStatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        INFECTED
                    )
            )
        }

        notInfectedContainer.setOnClickListener {
            startActivity(
                Intent(this, ChangeStatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        NOT_INFECTED
                    )
            )
        }

        positiveButton.setOnClickListener {
            startActivity(
                Intent(this, ChangeStatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        INFECTED
                    )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(
            "android.intent.action.MAYBE_INFECTED"
        )
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent
            ) {
                mViewModel.setMaybeInfected()
                if (intent.getIntExtra(STATUS_API_KEY, DEFAULT) == MAYBE_INFECTED) {
                    updateStatus(status = MAYBE_INFECTED)
                }
            }
        }

        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter)
    }

    override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause()

        //unregister our receiver
        this.unregisterReceiver(this.mReceiver)
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()

        //unregister our receiver
        this.unregisterReceiver(this.mReceiver)
    }

    override fun onServiceConnected(
        className: ComponentName,
        service: IBinder
    ) {
        val name = className.className
        if (name.endsWith("BackgroundLocationService")) {
            gpsService = (service as LocationServiceBinder).service
            Log.d("GPS Ready", "!!");
        }
        if (name.endsWith("StatusActivity")) {
            Log.d("SERVICE", "STARTED SERVICE --------------------------------------\n");
            Log.d("Status BLE Ready", "!!");
        }
    }

    override fun onServiceDisconnected(className: ComponentName) {
        if (className.className == "BackgroundLocationService") {
            gpsService = null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTracking()
            }
        }
    }

    fun stopTracking() {
        mTracking = false
        gpsService?.stopTracking()
    }

    fun startTracking() {
        //check for permission
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            gpsService?.startTracking()
            mTracking = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 200)
        }
    }


    private fun updateStatus(status: Int) {
        mCurrentStatusImage.setImageResource(
            when (status) {
                INFECTED -> R.drawable.infected_big
                MAYBE_INFECTED -> R.drawable.maybe_infected_big
                else -> R.drawable.not_infected_big
            }
        )
        mCurrentStatusText.text =
            when (status) {
                INFECTED -> resources.getString(R.string.infected_text)
                MAYBE_INFECTED -> resources.getString(R.string.maybe_infected)
                else -> resources.getString(R.string.not_infected)
            }
    }

    override fun onBeaconServiceConnect() {
        beaconManager?.removeAllMonitorNotifiers()
        beaconManager?.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region) {
                Log.d(
                    "MonitoringActivity.TAG",
                    "I just saw an beacon for the first time!"
                )
            }

            override fun didExitRegion(region: Region) {
                Log.d("MonitoringActivity.TAG", "I no longer see an beacon")
            }

            override fun didDetermineStateForRegion(
                state: Int,
                region: Region
            ) {
                Log.d(
                    "MonitoringActivity.TAG",
                    "I have just switched from seeing/not seeing beacons: $state"
                )
            }
        })

            try {
                beaconManager?.startMonitoringBeaconsInRegion(
                    Region(
                        "myMonitoringUniqueId",
                        null,
                        null,
                        null
                    )
                )
            } catch (e: RemoteException) {}
    }
}
