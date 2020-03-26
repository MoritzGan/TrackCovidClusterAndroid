package de.trackcovidcluster.status

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.status.Constants.INFECTED
import de.trackcovidcluster.status.Constants.MAYBE_INFECTED
import de.trackcovidcluster.status.Constants.NOT_INFECTED
import de.trackcovidcluster.status.Constants.STATUS_KEY
import de.trackcovidcluster.tracking.BackgroundLocationService
import de.trackcovidcluster.tracking.BackgroundLocationService.LocationServiceBinder
import kotlinx.android.synthetic.main.activity_status.*
import javax.inject.Inject


class StatusActivity : AppCompatActivity() {

    companion object {
        private const val DEFAULT = -1
    }

    // region members
    private lateinit var mViewModel: ChangeStatusViewModel
    private val PERMISSIONS_REQUEST = 100
    var gpsService: BackgroundLocationService? = null
    var mTracking = false

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var mCurrentStatusImage: ImageView
    private lateinit var mCurrentStatusText: TextView
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(ChangeStatusViewModel::class.java)

        // Get Permissions for Tracking
        val lm =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish()
        }

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Start Tracking here
        } else {

            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
        }

        mCurrentStatusImage = currentStatusImage
        mCurrentStatusText = currentStatusText

        val status = intent.getIntExtra(STATUS_KEY, DEFAULT)
        if (status != DEFAULT) {
            updateStatus(status = status)
        }

        /**
         * Testing
         * TODO Start tracking
         */
        //prepare service
        val intent = Intent(this.getApplication(), BackgroundLocationService::class.java)
        this.getApplication().startService(intent)
        this.getApplication()
            .bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        fun stopTracking() {
            mTracking = false;
            gpsService?.stopTracking();
        }

        fun startTracking() {
            //check for permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gpsService?.startTracking();
                mTracking = true;
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 200);
            }
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            @NonNull permissions: Array<String?>?,
            @NonNull grantResults: IntArray
        ) {
            if (permissions != null) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
            if (requestCode == 200) {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    startTracking()
                }
            }
        }

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

    val serviceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val name = className.className
            if (name.endsWith("BackgroundLocationService")) {
                gpsService = (service as LocationServiceBinder).getService()
                Log.d("GPS Ready", "!!");
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            if (className.className == "BackgroundLocationService") {
                gpsService = null
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
}
