package de.trackcovidcluster.main

import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun checkUser() {
        if (!mUserStorageSource.isUserExisting()) {
            mUserStorageSource.createUser()
        }
    }
}


