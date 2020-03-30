package de.trackcovidcluster.status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
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

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    private lateinit var mReportTop: TextView
    private lateinit var mReportBottom: TextView
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
        mReportTop = reportTop
        mReportBottom = reportBottom

        val status = intent.getIntExtra(STATUS_KEY, DEFAULT)
        if (status != DEFAULT) {
            updateStatus(status = status)
        } else {
            val currentStatus = mViewModel.getStatusFromSource()
            updateStatus(status = currentStatus)
        }

        // Will return sth. like 266290753698018418588485362138100705178
        var hashedPubKey: BigInteger = mViewModel.getPublicKeyInInt()

        // TODO split the hashedPubKey to minor/majors of the beacons

        setBeaconTransmitter()
        setBeaconTransmitter()
        setBeaconTransmitter()
        setBeaconTransmitter()

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
        if (mReceiver != null) {
            this.registerReceiver(mReceiver, intentFilter)
        }
    }

    override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause()

        //unregister our receiver
        this.unregisterReceiver(mReceiver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setBeaconTransmitter() {
        // TODO Change UUID to one of the Server ones

        val beacon: Beacon? = mViewModel.getBeacon(
            UUID.randomUUID().toString(), "1", "f"
        )


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

        if (status == MAYBE_INFECTED) {
            mReportBottom.visibility = View.VISIBLE
            mReportTop.visibility = View.VISIBLE
        } else {
            mReportBottom.visibility = View.INVISIBLE
            mReportTop.visibility = View.INVISIBLE
        }
    }
}
