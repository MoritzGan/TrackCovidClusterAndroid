package de.trackcovidcluster.main

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.status.StatusActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    // region members
    private lateinit var mViewModel: MainActivityViewModel
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel::class.java)

        val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
        val doubleBounce: Sprite = DoubleBounce()
        progressBar.indeterminateDrawable = doubleBounce
        progressBar.visibility = View.VISIBLE

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 100)
        }

        mViewModel.getUUIDsFromServer()

        Handler().postDelayed(Runnable {
            // Log User in
            if (mViewModel.isFirstTimeUser()) {
                mainScreen.visibility = View.VISIBLE
                startButtonBottom.setOnClickListener {

                    mViewModel.createUser()
                    mViewModel.generateKeyPair()

                    startActivity(
                        Intent(this, StatusActivity::class.java)
                    )
                    finish()
                }
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


}
