package de.trackcovidcluster.status

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_status.*
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import java.math.BigInteger
import java.util.*
import javax.inject.Inject


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StatusActivity : AppCompatActivity() {

    companion object {
        private const val DEFAULT = -1
    }

    // region members
    private lateinit var mViewModel: StatusViewModel
    private val PERMISSIONS_REQUEST = 100
    private lateinit var mUserStorageSource: IUserStorageSource

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

        // Will return sth. like 2662 9075 3698 0184 1858 8485 3621 3810 0705 178
        var hashedPubKey : BigInteger = mViewModel.getPublicKeyInInt()

        // TODO split the hashedPubKey to minor/majors of the beacons
        val splitOne : Int = hashedPubKey.toString(10).substring(0, 4).toInt()
        val splitTwo : Int = hashedPubKey.toString(10).substring(4, 8).toInt()
        val splitThree : Int = hashedPubKey.toString(10).substring(8, 12).toInt()
        val splitFour : Int = hashedPubKey.toString(10).substring(12, 16).toInt()
        val splitFive : Int = hashedPubKey.toString(10).substring(16, 20).toInt()
        val splitSix : Int = hashedPubKey.toString(10).substring(20, 24).toInt()
        val splitSeven : Int = hashedPubKey.toString(10).substring(24, 28).toInt()
        val splitEight : Int = hashedPubKey.toString(10).substring(28, 32).toInt()
        val splitNine : Int = hashedPubKey.toString(10).substring(32, 36).toInt()
        val splitTen : Int = hashedPubKey.toString(10).substring(36, 39).toInt()

        setBeaconTransmitter(splitOne, splitTwo)
        setBeaconTransmitter(splitThree, splitFour)
        setBeaconTransmitter(splitFive, splitSix)
        setBeaconTransmitter(splitSeven, splitEight)
        setBeaconTransmitter(splitNine, splitTen)

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

    private fun setBeaconTransmitter(major : Int, minor : Int) {
        // TODO Change UUID to one of the Server ones

        val beacon: Beacon? = mViewModel.getBeacon(
            UUID.randomUUID().toString(), major.toString(), minor.toString())


        val beaconParser: BeaconParser = BeaconParser()
            .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")

        val beaconTransmitter =
            BeaconTransmitter(applicationContext, beaconParser)

        beaconTransmitter.startAdvertising(beacon)
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
}
