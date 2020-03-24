package de.trackcovidcluster.status

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import de.trackcovidcluster.status.Constants.STATUS_KEY
import kotlinx.android.synthetic.main.activity_change_status.*
import javax.inject.Inject

class ChangeStatusActivity : AppCompatActivity() {
    companion object {
        private const val DEFAULT = 0
    }

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

        getNextStatus(status)

        changeStatusButton.setOnClickListener {
            if (status == Constants.INFECTED) {
                mViewModel.sendStatus()
            }
            startActivity(
                Intent(this, StatusActivity::class.java)
                    .putExtra(
                        STATUS_KEY,
                        status
                    )
            )
        }

    }

    private fun getNextStatus(status: Int) {
        futureStausImage.setImageResource(
            when (status) {
                Constants.INFECTED -> R.drawable.infected_small
                Constants.MAYBE_INFECTED -> R.drawable.maybe_infected_small
                else -> R.drawable.not_infected_small
            }
        )

        futureStatusText.text =
            when (status) {
                Constants.INFECTED -> resources.getString(R.string.infected_text)
                Constants.MAYBE_INFECTED -> resources.getString(R.string.maybe_infected)
                else -> resources.getString(R.string.not_infected)
            }
    }

}
