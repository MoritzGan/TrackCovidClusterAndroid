package de.trackcovidcluster.status

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.AndroidInjection
import de.trackcovidcluster.Constants.DEFAULT
import de.trackcovidcluster.Constants.INFECTED
import de.trackcovidcluster.Constants.MAYBE_INFECTED
import de.trackcovidcluster.Constants.STATUS_API_KEY
import de.trackcovidcluster.Constants.STATUS_KEY
import de.trackcovidcluster.R
import de.trackcovidcluster.byteConverter.uuidHelper
import de.trackcovidcluster.changeStatus.ChangeStatusActivity
import de.trackcovidcluster.database.DatabaseHelper
import de.trackcovidcluster.models.Cookie
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.json.JSONObject
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class StatusActivity : AppCompatActivity(), BeaconConsumer {

    companion object {
        private const val SCANNER_TAG = "Scanning"
        private const val ADVERTISING_TAG = "Advertising"
    }

    private var uuids: JSONObject? = null
    private var contacts: HashMap<String, Beacon>? = null
    private var contactsDistance: HashMap<String, String>? = null
    private var contactsUUIDs: HashMap<String, String>? = null
    private var mReceiver: BroadcastReceiver? = null
    private var isAdvertising: Boolean = false

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: StatusViewModel
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private lateinit var mStatusTextView: TextView
    private lateinit var mBeaconManager: BeaconManager
    private lateinit var mBackgroundPowerSaver: BackgroundPowerSaver
    private lateinit var beaconParser: BeaconParser
    private lateinit var mReportBottomText: TextView
    private lateinit var mReportTopText: TextView
    private lateinit var mPositiveButton: Button
    private lateinit var beaconTransmitter: BeaconTransmitter

    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
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
        mPositiveButton = positiveButton
        mBeaconManager = BeaconManager.getInstanceForApplication(this)
        mBackgroundPowerSaver = BackgroundPowerSaver(this)

        val beaconParser: BeaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        beaconTransmitter =
            BeaconTransmitter(applicationContext, beaconParser)

        /**
         * Get the Permissions for BLE Usage
         */

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {}

            override fun onPermissionDenied(deniedPermissions: List<String?>) {}
        }

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN
            )
            .check();

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
                updateStatus(status = mViewModel.getStatusFromSource())
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

        startBeaconService()
        startAdvertising()
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

    @SuppressLint("SetTextI18n")
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    override fun onResume() {
        super.onResume()

        startBeaconService()

        val db = DatabaseHelper(this)
        mStatusTextView.text = "Clustergröße: ${db.profilesCount}"

        val intentFilter = IntentFilter("android.intent.action.MAYBE_INFECTED")

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


    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    override fun onPause() {
        super.onPause()
        if (applicationContext != null) startAdvertising()
        this.unregisterReceiver(this.mReceiver)
    }

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    override fun onDestroy() {
        super.onDestroy()
        if (applicationContext != null) startAdvertising()
        this.unregisterReceiver(this.mReceiver)
    }

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    override fun onBackPressed() {
        super.onBackPressed()
        if (applicationContext != null) startAdvertising()
        finish()
    }

    /**
     * BLE Functions for scanning and creating the encrypted payload
     */

    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    override fun onBeaconServiceConnect() {

        mBeaconManager.removeAllRangeNotifiers()
        val trackCovidIdentifier: Identifier = Identifier.parse("1111")

        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {
                for (beacon in beacons)
                {
                    if (beacon.id2 == trackCovidIdentifier
                        && beacon.id3 == trackCovidIdentifier
                        && !contactsUUIDs!!.contains(beacon.id1.toHexString()))
                    {
                        val hashedUUIDReceived: ByteArray = uuidHelper.getBytesFromUUID(beacon.id1.toUuid());
                        contactsUUIDs?.put(beacon.id1.toHexString(), System.currentTimeMillis().toString());
                        createPayload(hashedUUIDReceived);
                    }
                }
            }
        }

        try {
            mBeaconManager.startRangingBeaconsInRegion(
                Region(
                    "myRangingUniqueId",
                    null,
                    Identifier.parse("1111"),
                    Identifier.parse("1111")
                )
            )
        } catch (e: RemoteException) {
            Log.e(SCANNER_TAG, e.toString())
        }
    }

    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    private fun createPayload(hashedUUIDReceived: ByteArray) {

        val db = DatabaseHelper(this)
        val publicKeyFromServer = mViewModel.getServerPubKey()
        val publicKeyFromServerDecoded: ByteArray =
            Base64.decode(publicKeyFromServer, Base64.NO_WRAP)

        val fetchedUUIDDecoded: String =
            Base64.encodeToString(hashedUUIDReceived, Base64.NO_WRAP)

        val cookie = Cookie(fetchedUUIDDecoded, System.currentTimeMillis())

        Log.d(SCANNER_TAG,
                "Created Cookie: :\n{\n" +
                    "UUID: " + cookie.hashedUUID + "\n" +
                    "Time: " + cookie.timestamp + "\n" +
                    "}\n"
        )

        db.insertDataSet(cookie, publicKeyFromServerDecoded)
        statusTextView.text = db.cookieBundle.size.toString()
    }


    /**
     * Functions for setting the beacons to Advertise themselves
     */

    private fun setBeaconTransmitter(uuid: String) {

            val beacon = Beacon.Builder()
            .setId1(uuid)
            .setId2("1111")
            .setId3("1111")
            .setManufacturer(0x004c)
            .setTxPower(-59)
            .build()

            if (applicationContext != null && !isAdvertising){
                beaconTransmitter.startAdvertising(beacon)
                isAdvertising = true;
                Toast.makeText(this, "Ich bin jetzt sichtbar!", Toast.LENGTH_LONG).show()
            }
    }

    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    private fun startAdvertising() {

        val hashedPubKey: ByteArray = mViewModel.getPublicKeyByteArray();
        val hashedPubKeyAsUUID: String = uuidHelper.getUUIDFromBytes(hashedPubKey).toString();
        setBeaconTransmitter(hashedPubKeyAsUUID);
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

        mPositiveButton.apply {
            isEnabled = status != INFECTED
        }

        if (status == MAYBE_INFECTED) {
            mReportTopText.visibility = View.VISIBLE
            mReportBottomText.visibility = View.VISIBLE
        } else {
            mReportTopText.visibility = View.INVISIBLE
            mReportBottomText.visibility = View.INVISIBLE
        }
    }

    @ExperimentalUnsignedTypes
    @ExperimentalStdlibApi
    private fun startBeaconService() {
        if (!mBeaconManager.isAnyConsumerBound) {

            val intent = Intent(this, this::class.java)
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Aktiv und Funktionstüchtig")
                .setContentIntent(pendingIntent)

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
    }
}
