package de.trackcovidcluster.main

import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun isFirstTimeUser(): Boolean =
        !mUserStorageSource.isUserExisting()

    fun createUser() = mUserStorageSource.createUser()
}


