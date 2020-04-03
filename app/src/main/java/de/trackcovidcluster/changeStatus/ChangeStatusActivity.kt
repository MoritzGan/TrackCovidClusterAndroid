package de.trackcovidcluster.changeStatus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.database.DatabaseHelper
import de.trackcovidcluster.status.Constants.DEFAULT
import de.trackcovidcluster.status.Constants.INFECTED
import de.trackcovidcluster.status.Constants.MAYBE_INFECTED
import de.trackcovidcluster.status.Constants.STATUS_KEY
import de.trackcovidcluster.status.StatusActivity
import kotlinx.android.synthetic.main.activity_change_status.*
import javax.inject.Inject

class ChangeStatusActivity : AppCompatActivity() {
    // region members
    private lateinit var mViewModel: ChangeStatusViewModel

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_status)

        mViewModel =
            ViewModelProviders.of(this, mViewModelFactory).get(ChangeStatusViewModel::class.java)

        val status = this.intent.getIntExtra(STATUS_KEY, DEFAULT)

        val db = DatabaseHelper(this)
        val encounters = db.cookieBundle
        Log.d("Database", " " + db.cookieBundle)

        getNextStatus(status)

        changeStatusButton.setOnClickListener {

            mViewModel.sendStatus(encounters as ArrayList<String?>) // Send the encrypted cookies to the server
            db.delteAllCookies()                                    // Delete the local encounters

            startActivity(
                Intent(this, StatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        INFECTED
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

    }

    private fun getNextStatus(status: Int) {
        futureStausImage.setImageResource(
            when (status) {
                INFECTED -> R.drawable.infected_big
                MAYBE_INFECTED -> R.drawable.maybe_infected_big
                else -> R.drawable.not_infected_small
            }
        )

        futureStatusText.text =
            when (status) {
                INFECTED -> resources.getString(R.string.infected_text)
                MAYBE_INFECTED -> resources.getString(R.string.maybe_infected)
                else -> resources.getString(R.string.not_infected)
            }
    }

}
