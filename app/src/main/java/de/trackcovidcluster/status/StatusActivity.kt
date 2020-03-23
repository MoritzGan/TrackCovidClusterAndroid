package de.trackcovidcluster.status

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.trackcovidcluster.R
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    companion object {
        private const val NOT_INFECTED = 0
        private const val MAYBE_INFECTED = 1
        private const val INFECTED = 2
        private const val STATUS_KEY = "status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

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
    }
}
