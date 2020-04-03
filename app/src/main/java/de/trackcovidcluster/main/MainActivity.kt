package de.trackcovidcluster.main

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.status.StatusActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_FINE_LOCATION = 1
    private val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2

    // region members
    private lateinit var mViewModel: MainActivityViewModel
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    // endregion
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel::class.java)
        
        checkPermissions()
        mViewModel.getUUIDsFromServer()

        Handler().postDelayed({
            // Log User in
            if (mViewModel.isFirstTimeUser()) {
                mainScreen.visibility = View.VISIBLE
                startButtonTop.setOnClickListener {

                    mViewModel.createUser()
                    mViewModel.generateKeyPair()

                    startActivity(
                        Intent(this, StatusActivity::class.java)
                    )
                    finish()
                }
            } else {
                startActivity(
                    Intent(this, StatusActivity::class.java)
                )
                finish()
            }
        }, 5000)
    }

    private fun checkPermissions() {

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 100)
        }


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
                                    2
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
                        1
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

        fun onRequestPermissionsResult(
            statusActivity: StatusActivity, requestCode: Int,
            permissions: Array<String?>?, grantResults: IntArray
        ) {
            when (requestCode) {
                PERMISSION_REQUEST_FINE_LOCATION -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("TAG", "fine location permission granted")
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
                        Log.d("MainActivity", "background location permission granted")
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
}
