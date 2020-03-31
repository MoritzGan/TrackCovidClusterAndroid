package de.trackcovidcluster.status

import android.Manifest
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.changeStatus.ChangeStatusActivity
import de.trackcovidcluster.database.DatabaseHelper
import de.trackcovidcluster.models.Cookie
import de.trackcovidcluster.status.Constants.INFECTED
import de.trackcovidcluster.status.Constants.MAYBE_INFECTED
import de.trackcovidcluster.status.Constants.NOT_INFECTED
import de.trackcovidcluster.status.Constants.STATUS_API_KEY
import de.trackcovidcluster.status.Constants.STATUS_KEY
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.altbeacon.bluetooth.BluetoothMedic
import org.json.JSONObject
import java.math.BigInteger
import java.security.Timestamp
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class StatusActivity : AppCompatActivity(), BeaconConsumer {

    companion object {
        private const val DEFAULT = -1
        protected val TAG = "MonitoringActivity"
        private val PERMISSION_REQUEST_FINE_LOCATION = 1
        private val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2
        protected fun onRequestPermissionsResult(
            statusActivity: StatusActivity, requestCode: Int,
            permissions: Array<String?>?, grantResults: IntArray
        ) {
            when (requestCode) {
                PERMISSION_REQUEST_FINE_LOCATION -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "fine location permission granted")
                    } else {
                        val builder =
                            AlertDialog.Builder(statusActivity)
                        builder.setTitle("Functionality limited")
                        builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.")
                        builder.setPositiveButton(android.R.string.ok, null)
                        builder.setOnDismissListener { }
                        builder.show()
                    }
                    return
                }
                PERMISSION_REQUEST_BACKGROUND_LOCATION -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "background location permission granted")
                    } else {
                        val builder =
                            AlertDialog.Builder(statusActivity)
                        builder.setTitle("Functionality limited")
                        builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.")
                        builder.setPositiveButton(android.R.string.ok, null)
                        builder.setOnDismissListener { }
                        builder.show()
                    }
                    return
                }
            }
        }
    }

    private var uuids: JSONObject? = null
    private var contacts: HashMap<String, String>? = null
    private var contactsDistance: HashMap<String, String>? = null
    private var contactsUUIDs: HashMap<String, String>? = null
    private var contactsAlreadyInDb: ArrayList<String>? = null
    private var mReceiver: BroadcastReceiver? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: StatusViewModel
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private lateinit var mBeaconManager: BeaconManager
    private lateinit var mBackgroudPowerSaver: BackgroundPowerSaver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mBeaconManager = BeaconManager.getInstanceForApplication(this)
        mBackgroudPowerSaver = BackgroundPowerSaver(this)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(StatusViewModel::class.java)

        mViewModel.getStatus()
        uuids = mViewModel.getUUIDs()
        contacts = HashMap()
        contactsUUIDs = HashMap()
        contactsDistance = HashMap()
        contactsAlreadyInDb = ArrayList() // TODO Sync with db
        mCurrentStatusImage = currentStatusImage
        mCurrentStatusText = currentStatusText

        val medic: BluetoothMedic = BluetoothMedic.getInstance()
        medic.enablePowerCycleOnFailures(this)
        medic.enablePeriodicTests(this, BluetoothMedic.SCAN_TEST or BluetoothMedic.TRANSMIT_TEST)

        val status = intent.getIntExtra(STATUS_KEY, DEFAULT)
        if (status != DEFAULT) {
            updateStatus(status = status)
        } else {
            val currentStatus = mViewModel.getStatusFromSource()
            updateStatus(status = currentStatus)
        }

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
        startAdvertising()

        verifyBluetooth()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            val builder =
                                AlertDialog.Builder(this)
                            builder.setTitle("This app needs background location access")
                            builder.setMessage("Please grant location access so this app can detect beacons in the background.")
                            builder.setPositiveButton(android.R.string.ok, null)
                            builder.setOnDismissListener {
                                requestPermissions(
                                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                                    StatusActivity.PERMISSION_REQUEST_BACKGROUND_LOCATION
                                )
                            }
                            builder.show()
                        } else {
                            val builder =
                                AlertDialog.Builder(this)
                            builder.setTitle("Functionality limited")
                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.")
                            builder.setPositiveButton(android.R.string.ok, null)
                            builder.setOnDismissListener { }
                            builder.show()
                        }
                    }
                }
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ),
                        StatusActivity.PERMISSION_REQUEST_FINE_LOCATION
                    )
                } else {
                    val builder =
                        AlertDialog.Builder(this)
                    builder.setTitle("Functionality limited")
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
            }
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
        this.registerReceiver(mReceiver, intentFilter)
        startAdvertising()
    }

    override fun onPause() {
        super.onPause()

        this.unregisterReceiver(this.mReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()

        mBeaconManager.unbind(this)
    }

    /**
     * Functions for monitoring
     */

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBeaconServiceConnect() {
        //mBeaconManager.removeAllRangeNotifiers()

        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {
                Log.i(
                    "See Beacon",
                    "The first beacon I see is about " + beacons.iterator().next().distance + " meters away."
                )
                Log.i(
                    "Details:", "Found :" + beacons.size +
                            " Beacons. UUID: " + beacons.iterator().next().id1
                )

                for (beacon in beacons) {
                    if(!contacts!!.containsKey(beacon.id1.toString())) {
                        contacts!![beacon.id1.toString()] = beacon.id2.toString() + (beacon.id3).toString()
                        contactsDistance!![beacon.id1.toString()] = beacon.distance.toString()
                    }
                    if(!contactsAlreadyInDb?.contains(beacon.id1.toString())!!){
                        contactsAlreadyInDb!!.add(beacon.id1.toString())
                        createPayload(contacts!!)
                    }
                }
            }
        }
        try {
            mBeaconManager.startRangingBeaconsInRegion(
                Region(
                    "myRangingUniqueId",
                    null,
                    null,
                    null
                )
            )
            Log.d("Looking for beacons", "looking for beacons ")
        } catch (e: RemoteException) {
            Log.e("Dont see Beacon", e.toString())
        }
    }

    private fun verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Bluetooth not enabled")
                builder.setMessage("Please enable bluetooth in settings and restart this application.")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setOnDismissListener {
                    //finish();
                    //System.exit(0);
                }
                builder.show()
            }
        } catch (e: RuntimeException) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bluetooth LE not available")
            builder.setMessage("Sorry, this device does not support Bluetooth LE.")
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener {
                //finish();
                //System.exit(0);
            }
            builder.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPayload(contacs : HashMap<String, String>) {
        var uuidOfContact: String? = ""
        var beaconCounter: Int = 0
        val db: DatabaseHelper = DatabaseHelper(this)
        val publicKey: String? = mViewModel.getServerPubKey()

        for (beacon in contacs) {
            for (char in beacon.value) {
                uuidOfContact += "" + char
            }
            beaconCounter++

            if (beaconCounter == 5) {
                val uuidContactHex: String = BigInteger(uuidOfContact).toString(16)
                if (!contactsUUIDs!!.containsKey(uuidContactHex)) {
                    contactsUUIDs!![uuidContactHex] = System.currentTimeMillis().toString()
                    val cookie: Cookie = Cookie(uuidContactHex, System.currentTimeMillis())
                    db.insertDataSet(cookie, publicKey)
                }
            }
        }
    }

    /**
     * Functions for setting the beacons to Advertise themselfs.
     */

    private fun setBeaconTransmitter(major: Int?, minor: Int?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        val uuids: JSONObject = mViewModel.getUUIDs()

        if (!uuids.isNull("0")) {
            var uuid: String = uuids.getString(counter.toString())

            Log.d("UUIDS", "   " + uuids)

            val beacon: Beacon? = mViewModel.getBeacon(
                uuid, major.toString(), minor.toString()
            )

            val beaconParser: BeaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")

            val beaconTransmitter =
                BeaconTransmitter(applicationContext, beaconParser)

            mBeaconManager.beaconParsers.add(beaconParser)
            mBeaconManager.bind(this)

            beaconTransmitter.startAdvertising(beacon)
        }
    }

    private fun setBeaconTransmitterLast(major: Int?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        var uuids: JSONObject = mViewModel.getUUIDs()

        val beacon: Beacon? = mViewModel.getBeacon(
            uuids.getString(counter.toString()), major.toString(), ""
        )


        val beaconParser: BeaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")

        mBeaconManager.beaconParsers.add(beaconParser)
        mBeaconManager.bind(this)

        val beaconTransmitter =
            BeaconTransmitter(applicationContext, beaconParser)

        beaconTransmitter.startAdvertising(beacon)
    }

    private fun startAdvertising() {

        val hashedPubKey: BigInteger = mViewModel.getPublicKeyInInt()
        val keyAsString = hashedPubKey.toString()
        var counter = 0

        for (x in keyAsString.indices) {
            var oneStr: String?
            var twoStr: String?

            if ((x % 8 == 0 && x != 0 && x <= keyAsString.length) || x == 4) {

                if ((x + 4) < keyAsString.length) {

                    if (x == 4) {
                        oneStr = keyAsString.substring(0, 4)
                        twoStr = keyAsString.substring(4, 8)
                        setBeaconTransmitter(oneStr.toInt(), twoStr.toInt(), counter)

                        Log.d("SET BEACON (", " " + oneStr + "  " + twoStr + ")\n");
                        counter++
                    } else {
                        oneStr = keyAsString.substring(x - 4, x)
                        twoStr = keyAsString.substring(x, x + 4)
                        setBeaconTransmitter(oneStr.toInt(), twoStr.toInt(), counter)

                        Log.d("SET BEACON (", " " + oneStr + "  " + twoStr + ")\n");
                        counter++
                    }

                } else if ((x + 4) > keyAsString.length) {
                    setBeaconTransmitterLast(keyAsString.substring(x).toInt(), counter)

                    Log.d("SET BEACON (", " " + keyAsString.substring(x).toInt() + ")\n");
                    counter++
                }
            }
        }
    }

    /**
     * Update the users State
     */

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
}
