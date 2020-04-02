package de.trackcovidcluster.status

import android.app.AlertDialog
import android.app.Notification.Builder
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import de.trackcovidcluster.status.Constants.STATUS_API_KEY
import de.trackcovidcluster.status.Constants.STATUS_KEY
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.altbeacon.bluetooth.BluetoothMedic
import org.json.JSONObject
import java.math.BigInteger
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class StatusActivity : AppCompatActivity(), BeaconConsumer {

    companion object {
        private const val DEFAULT = -1
    }

    private var uuids: JSONObject? = null
    private var contacts: HashMap<String, String>? = null
    private var contactsDistance: HashMap<String, String>? = null
    private var contactsUUIDs: HashMap<String, String>? = null
    private var mReceiver: BroadcastReceiver? = null
    private var mShouldCreatePayload: Boolean = false

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: StatusViewModel
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private lateinit var mStatusTextView: TextView
    private lateinit var mBeaconManager: BeaconManager
    private lateinit var mBackgroudPowerSaver: BackgroundPowerSaver
    private lateinit var beaconParser: BeaconParser
    private lateinit var mReportBottomText: TextView
    private lateinit var mReportTopText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(StatusViewModel::class.java)
        mViewModel.getStatus()
        uuids = mViewModel.getUUIDs()
        contacts = HashMap()
        contactsUUIDs = HashMap()
        contactsDistance = HashMap()
        mCurrentStatusImage = currentStatusImage
        mStatusTextView = statusTextView
        mCurrentStatusText = currentStatusText

        /**
         * Setuo the beaconService to run in the foreground
         */

        mBeaconManager = BeaconManager.getInstanceForApplication(this)
        mBackgroudPowerSaver = BackgroundPowerSaver(this)

        if (!mBeaconManager.isAnyConsumerBound) {
            val builder = Builder(this)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle("Aktiv und Funktionstüchtig")
            val intent = Intent(this, this::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(pendingIntent)

            beaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
            mBeaconManager.beaconParsers.add(beaconParser)
            mBeaconManager.backgroundBetweenScanPeriod = 0;
            mBeaconManager.backgroundScanPeriod = 2000;
            mBeaconManager.foregroundScanPeriod = 2000;
            mBeaconManager.enableForegroundServiceScanning(builder.build(), 456)
            mBeaconManager.setEnableScheduledScanJobs(false)
            mBeaconManager.bind(this)
        }

        /**
         * Check the Status and change on change from Server or on Infection
         * submission.
         */

        val status = intent.getIntExtra(STATUS_KEY, DEFAULT)
        val statusApi = intent.getIntExtra(STATUS_API_KEY, DEFAULT)

        when {
            statusApi != DEFAULT -> updateStatus(status = statusApi)
            status != DEFAULT -> updateStatus(status = status)
            else -> {
                val currentStatus = mViewModel.getStatusFromSource()
                updateStatus(status = currentStatus)
            }
        }

        positiveButton.setOnClickListener {
            mBeaconManager.unbind(this)
            startActivity(
                Intent(this, ChangeStatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        INFECTED
                    )
            )
        }

        /**
         * Check Permissions on Runtime for Location Service
         */

        verifyBluetooth()

        Handler().postDelayed({
            startAdvertising()
            Log.d("Started Advertising", " ")
        }, 5000)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val status = intent?.getIntExtra(STATUS_KEY, DEFAULT)

        status?.let {
            when {
                status != DEFAULT -> updateStatus(status = status)
                else -> {
                    val currentStatus = mViewModel.getStatusFromSource()
                    updateStatus(status = currentStatus)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!mBeaconManager.isAnyConsumerBound) {
            mBeaconManager = BeaconManager.getInstanceForApplication(this)
            mBackgroudPowerSaver = BackgroundPowerSaver(this)

            val builder = Builder(this)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setContentTitle("Aktiv und Funktionstüchtig")
            val intent = Intent(this, this::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(pendingIntent)

            beaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
            mBeaconManager.beaconParsers.add(beaconParser)
            mBeaconManager.backgroundBetweenScanPeriod = 0;
            mBeaconManager.backgroundScanPeriod = 2000;
            mBeaconManager.foregroundScanPeriod = 2000;
            mBeaconManager.enableForegroundServiceScanning(builder.build(), 456)
            mBeaconManager.setEnableScheduledScanJobs(false)
            mBeaconManager.bind(this)
            startAdvertising()
        }

        val db = DatabaseHelper(this)
        mStatusTextView.text = "Clustergröße: " + db.profilesCount

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
    }

    override fun onPause() {
        super.onPause()
        if (applicationContext != null) startAdvertising()
        this.unregisterReceiver(this.mReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (applicationContext != null) startAdvertising()
    }

    /**
     * BLE Functions for scanning and creating the encrypted payload
     */

    override fun onBeaconServiceConnect() {
        mBeaconManager.removeAllRangeNotifiers()

        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {

                Log.i(
                    "Details:", "Found :" + beacons.size +
                            " Beacons. UUID: " + beacons.iterator().next().id1 +
                            " in ca distance of " + beacons.iterator().next().distance + " m"
                )

                for (beacon in beacons) {
                    if (!contacts!!.containsKey(beacon.id1.toString()) && beacon.distance < 2.0 && beacons.size == 5) {

                        contacts!![beacon.id1.toString()] =
                            beacon.id2.toString() + (beacon.id3).toString()
                        contactsDistance!![beacon.id1.toString()] = beacon.distance.toString()
                        mShouldCreatePayload = true

                    } else {
                        mShouldCreatePayload = false
                    }
                }

                if (mShouldCreatePayload) {
                    Log.d(
                        "Counted as Contact!",
                        "This is saved as a contact!" + beacons.iterator().next().id1
                    )
                    createPayload(contacts!!)
                    var db: DatabaseHelper = DatabaseHelper(this)
                    mStatusTextView.text = "Clustergröße: " + db.profilesCount
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
                }
                builder.show()
            }
        } catch (e: RuntimeException) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bluetooth LE not available")
            builder.setMessage("Sorry, this device does not support Bluetooth LE.")
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener {
            }
            builder.show()
        }
    }

    private fun createPayload(contacs: HashMap<String, String>) {
        var uuidOfContact: String? = ""
        var beaconCounter = 0
        val db = DatabaseHelper(this)
        val publicKey: String? = mViewModel.getServerPubKey()

        for (beacon in contacs) {
            for (char in beacon.value) {
                uuidOfContact += "" + char
            }
            beaconCounter++

            if (beaconCounter == 4) {
                val uuidContactHex: String = BigInteger(uuidOfContact).toString(16)

                if (!contactsUUIDs!!.containsKey(uuidContactHex)) {
                    contactsUUIDs!![uuidContactHex] = System.currentTimeMillis().toString()
                    val cookie = Cookie(uuidContactHex, System.currentTimeMillis())
                    db.insertDataSet(cookie, publicKey)
                }
            }
        }
    }

    /**
     * Functions for setting the beacons to Advertise themselves
     */

    private fun setBeaconTransmitter(major: Int?, minor: Int?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        val uuids: JSONObject = mViewModel.getUUIDs()

        if (!uuids.isNull("0")) {
            var uuid: String = uuids.getString(counter.toString())

            val beacon: Beacon? = mViewModel.getBeacon(
                uuid, major.toString(), minor.toString()
            )

            val beaconParser: BeaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
            val beaconTransmitter =
                BeaconTransmitter(applicationContext, beaconParser)

            if (applicationContext != null) beaconTransmitter.startAdvertising(beacon)
            else Toast.makeText(this, "FAILED TO ADVERTISE", Toast.LENGTH_LONG).show()
        }
    }

    private fun setBeaconTransmitterLast(major: Int?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        val uuids: JSONObject = mViewModel.getUUIDs()

        val beacon: Beacon? = mViewModel.getBeacon(
            uuids.getString(counter.toString()), major.toString(), ""
        )
        val beaconParser: BeaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        val beaconTransmitter =
            BeaconTransmitter(applicationContext, beaconParser)

        if (applicationContext != null) beaconTransmitter.startAdvertising(beacon)
        else Toast.makeText(this, "FAILED TO ADVERTISE", Toast.LENGTH_LONG).show()
    }

    private fun startAdvertising() {

        val hashedPubKey: BigInteger = mViewModel.getPublicKeyInInt()
        val keyAsString = hashedPubKey.toString()
        var counter = 0

        for (x in keyAsString.indices) {
            var oneStr: String?
            var twoStr: String?

            if ((x % 8 == 0 && x != 0 && x <= keyAsString.length) || x == 4 && counter <= 5) {

                if ((x + 4) < keyAsString.length) {

                    if (x == 4) {
                        oneStr = keyAsString.substring(0, 4)
                        twoStr = keyAsString.substring(4, 8)
                        setBeaconTransmitter(oneStr.toInt(), twoStr.toInt(), counter)

                        counter++
                    } else {
                        oneStr = keyAsString.substring(x - 4, x)
                        twoStr = keyAsString.substring(x, x + 4)
                        setBeaconTransmitter(oneStr.toInt(), twoStr.toInt(), counter)

                        counter++
                    }

                } else if ((x + 4) > keyAsString.length) {
                    setBeaconTransmitterLast(keyAsString.substring(x).toInt(), counter)

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
