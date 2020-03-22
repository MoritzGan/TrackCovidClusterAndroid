package de.trackcovidcluster.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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

        if (mViewModel.isFirstTimeUser()) {
            mainScreen.visibility = View.VISIBLE
            startButtonBottom.setOnClickListener {
                mViewModel.createUser()
                startActivity(
                    Intent(this, StatusActivity::class.java)
                )
            }
            startButtonTop.setOnClickListener {
                mViewModel.createUser()
                startActivity(
                    Intent(this, StatusActivity::class.java)
                )
            }
        } else {
            startActivity(
                Intent(this, StatusActivity::class.java)
            )
        }
    }
}
