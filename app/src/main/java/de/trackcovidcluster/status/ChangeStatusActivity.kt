package de.trackcovidcluster.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
import kotlinx.android.synthetic.main.activity_change_status.*
import javax.inject.Inject
import javax.inject.Named

class ChangeStatusActivity : AppCompatActivity() {
    companion object {
        private const val STATUS = "status"
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

        val status = this.intent.getIntExtra(STATUS, DEFAULT)

        changeStatusButton.setOnClickListener {
            mViewModel.sendStatus()
        }

    }
}
