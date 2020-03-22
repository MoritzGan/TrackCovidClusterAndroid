package de.trackcovidcluster.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import de.trackcovidcluster.R
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

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel::class.java)

        mViewModel.checkUser()

    }
}
