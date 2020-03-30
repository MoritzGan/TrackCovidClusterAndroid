package de.trackcovidcluster.status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.changeStatus.ChangeStatusActivity
import de.trackcovidcluster.status.Constants.INFECTED
import de.trackcovidcluster.status.Constants.MAYBE_INFECTED
import de.trackcovidcluster.status.Constants.NOT_INFECTED
import de.trackcovidcluster.status.Constants.STATUS_API_KEY
import de.trackcovidcluster.status.Constants.STATUS_KEY
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.json.JSONObject
import java.math.BigInteger
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StatusActivity : AppCompatActivity(), BeaconConsumer {

    companion object {
        private const val DEFAULT = -1
    }

    // region members
    private lateinit var mViewModel: StatusViewModel

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private var mReceiver: BroadcastReceiver? = null
    private lateinit var mBeaconManager: BeaconManager
    private lateinit var mBackgroudPowerSaver: BackgroundPowerSaver
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mBeaconManager = BeaconManager.getInstanceForApplication(this)
        mBackgroudPowerSaver = BackgroundPowerSaver(this)

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
        startAdvertising()

        this.registerReceiver(mReceiver, intentFilter)
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

    /**
     * Advertises the hashed truncated public key in major and minor of 5 beacons.
     *
     * TODO: Set the UUIDs of the beacons to the ordered one from the server
     */

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
        mBeaconManager.removeAllMonitorNotifiers()
        mBeaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {
                Log.i(
                    "See Beacon",
                    "The first beacon I see is about " + beacons.iterator().next().distance + " meters away."
                )
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
}
