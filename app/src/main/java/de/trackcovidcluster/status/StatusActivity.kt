package de.trackcovidcluster.status

import android.app.AlertDialog
import android.app.Notification.Builder
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import de.trackcovidcluster.status.Constants.STATUS_API_KEY
import de.trackcovidcluster.status.Constants.STATUS_KEY
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBeaconServiceConnect() {
        mBeaconManager.removeAllRangeNotifiers()
        var counter = 0

        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {

                Log.i(
                    "Details:", "Found :" + beacons.size +
                            " Beacons. UUID: " + beacons.iterator().next().id1 +
                            " in ca distance of " + beacons.iterator().next().distance + " m"
                )

                for (beacon in beacons) {

                    if (!contacts!!.containsKey(beacon.id1.toString()) && beacon.distance < 2.0 && beacons.size == 5) {

                        contacts!![beacon.id1.toString()] = beacon.id2.toString() + (beacon.id3).toString()

                        //contactsDistance!![beacon.id1.toString()] = beacon.distance.toString()
                        mShouldCreatePayload = true

                    } else {
                        mShouldCreatePayload = false
                    }
                    counter++
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPayload(contacs: HashMap<String, String>) {
        var uuidOfContact: String? = ""
        var beaconCounter = 0
        val db = DatabaseHelper(this)
        val publicKey = mViewModel.getServerPubKey()
        var publicKeyDecoded: ByteArray = Base64.decode(publicKey, Base64.DEFAULT)

        for (beacon in contacs) {

            for (char in contacs["434c5553-5445-5254-5241-434b3030303$beaconCounter"].toString()) {
                uuidOfContact += "" + char
            }

            beaconCounter++

            if (beaconCounter == 4) {
                val uuidContactHex: String = Base64.encodeToString(
                    BigInteger(uuidOfContact).toString().toByteArray(),
                    Base64.NO_WRAP);

                if (!contactsUUIDs!!.containsKey(uuidContactHex)) {
                    contactsUUIDs!![uuidContactHex] = System.currentTimeMillis().toString()
                    val cookie = Cookie(uuidContactHex, System.currentTimeMillis())
                    db.insertDataSet(cookie, publicKeyDecoded)
                }
            }
        }
    }

    /**
     * Functions for setting the beacons to Advertise themselves
     */

    private fun setBeaconTransmitter(major: String?, minor: String?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        val uuids: JSONObject = mViewModel.getUUIDs()

        if (!uuids.isNull("0") && counter < 5) {
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

    @ExperimentalUnsignedTypes
    private fun startAdvertising() {

        val hashedPubKey: ByteArray = mViewModel.getPublicKeyInInt()
        val arrayOfBytesAsShort: ArrayList<Int> = ArrayList()

        var byteCounter = 1
        var beaconCounter = 0
        var minor = ""
        var major = ""

        for(byte in hashedPubKey) {

            if(byteCounter % 2 == 0 && byteCounter != 0) {

                var bytesAsShort: Int = bytesToUnsignedShort(
                    hashedPubKey[byteCounter - 2],
                    hashedPubKey[byteCounter - 1],
                    true)

                arrayOfBytesAsShort.add(bytesAsShort)
            }
            byteCounter++
        }

        for (i in 1 .. arrayOfBytesAsShort.size) {
            if(i % 2 == 0) {
                minor = arrayOfBytesAsShort[i - 2].toString()
                major = arrayOfBytesAsShort[i - 1].toString()

                setBeaconTransmitter(minor, major, beaconCounter)
                beaconCounter++
            }
            if(i == arrayOfBytesAsShort.size - 1 && i % 2 != 0) {
                minor = arrayOfBytesAsShort[i - 2].toString()
                major = ""
                setBeaconTransmitter(minor, major, beaconCounter)
                beaconCounter++
            }
        }

        Log.i("BEACON_SPAWNER", "Spawned $beaconCounter Beacons representing ${arrayOfBytesAsShort.toString()} ")
    }

    public fun bytesToUnsignedShort(byte1 : Byte, byte2 : Byte, bigEndian : Boolean) : Int {
        if (bigEndian)
            return (((byte1.toInt() and 255) shl 8) or (byte2.toInt() and 255))


        return (((byte2.toInt() and 255) shl 8) or (byte1.toInt() and 255))

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
