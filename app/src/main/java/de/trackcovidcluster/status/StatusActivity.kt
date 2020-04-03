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
import android.util.Base64
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
import org.json.JSONObject
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
        mReportTopText = reportTop
        mReportBottomText = reportBottom

        /**
         * Setup the beaconService to run in the foreground
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
        mBeaconManager.unbind(this)
        if (applicationContext != null) startAdvertising()
        this.unregisterReceiver(this.mReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBeaconManager.unbind(this)
        if (applicationContext != null) startAdvertising()
    }

    /**
     * BLE Functions for scanning and creating the encrypted payload
     */

    override fun onBeaconServiceConnect() {
        mBeaconManager.removeAllRangeNotifiers()
        var counter = 0
        var contactsCounter = 0
        var receivedBytes: ArrayList<Int> = arrayListOf<Int>()

        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {

                for (beacon in beacons) {
                    if (!contacts!!.containsKey(beacon.id1.toString()) && beacon.distance < 2.0 && beacons.size == 4) {
                        // Save the minor and major as seperated bytes
                        contacts!![beacon.id1.toString()] = beacon.id2.toString() + (beacon.id3).toString()
                        receivedBytes.add(beacon.id2.toInt())
                        receivedBytes.add(beacon.id3.toInt())
                        contactsCounter++
                        mShouldCreatePayload = true

                    } else {
                        mShouldCreatePayload = false
                    }
                    counter++
                }

                if (mShouldCreatePayload) {
                    Log.d(
                        "Counted as Contact!",
                        "This is saved as a contact!" + beacons.iterator().next().id1 +
                                " Payload " + receivedBytes.toString()
                    )
                    createPayload(receivedBytes)
                    var db = DatabaseHelper(this)
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

    @ExperimentalUnsignedTypes
    private fun createPayload(contacs: ArrayList<Int>) {
        var byteCounter = 1
        val resultAsUShoert: ArrayList<UShort> = ArrayList()
        var resultAsByteArray: ByteArray = byteArrayOf()
        val db = DatabaseHelper(this)
        val publicKey = mViewModel.getServerPubKey()
        val publicKeyDecoded: ByteArray = Base64.decode(publicKey, Base64.NO_WRAP)

        for (int in contacs) {
            resultAsUShoert.add(int.toUShort())
        }

        for (byteAsShort in resultAsUShoert) {
           resultAsByteArray += (byteAsShort.toByte())

            if (byteCounter == 8) {
                Log.d("SCANNER_RESULT", "Resulted ByteArray   "  + Base64.encodeToString(resultAsByteArray, Base64.NO_WRAP))
                Log.d("SCANNER_RESULT", "This users ByteArray "  + Base64.encodeToString(mViewModel.getPublicKeyByteArray(), Base64.NO_WRAP))
                Log.d("SCANNER_RESULT", "For Comparison: fetched $resultAsUShoert")

                val uuidBase64: String = Base64.encodeToString(resultAsByteArray, Base64.NO_WRAP)

                if (!contactsUUIDs!!.containsKey(uuidBase64)) {
                    contactsUUIDs!![uuidBase64] = System.currentTimeMillis().toString()
                    val cookie = Cookie(uuidBase64, System.currentTimeMillis())
                    db.insertDataSet(cookie, publicKeyDecoded)
                }
            }

            byteCounter++
        }
    }


    /**
     * Functions for setting the beacons to Advertise themselves
     */

    private fun setBeaconTransmitter(major: String?, minor: String?, counter: Int) {
        // TODO Change UUID to one of the Server ones
        val uuids: JSONObject = mViewModel.getUUIDs()

        if (!uuids.isNull("0") && counter < 5) {
            val uuid: String = uuids.getString(counter.toString())

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

        val hashedPubKey: ByteArray = mViewModel.getPublicKeyByteArray()
        val arrayOfBytesAsShort: ArrayList<UShort> = ArrayList()

        var beaconCounter = 0
        var minor: String
        var major: String

        hashedPubKey.map { byte ->
            arrayOfBytesAsShort.add(byte.toUShort())
        }

        // Should create 4 Beacons
        for (i in 1..arrayOfBytesAsShort.size) {
            if (i % 2 == 0) {
                minor = arrayOfBytesAsShort[i - 2].toString()
                major = arrayOfBytesAsShort[i - 1].toString()

                setBeaconTransmitter(minor, major, beaconCounter)
                beaconCounter++
            }
        }

        Log.i(
            "BEACON_SPAWNER_SCANNER",
            "Spawned $beaconCounter Beacons representing $arrayOfBytesAsShort "
        )
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

        if (status == INFECTED) {
            mReportTopText.visibility = View.VISIBLE
            mReportBottomText.visibility = View.VISIBLE
        } else {
            mReportTopText.visibility = View.INVISIBLE
            mReportBottomText.visibility = View.INVISIBLE
        }
    }
}
