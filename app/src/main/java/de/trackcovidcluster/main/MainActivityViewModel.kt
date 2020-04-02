package de.trackcovidcluster.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun isFirstTimeUser(): Boolean =
        !mUserStorageSource.isUserExisting()

    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser() = mUserStorageSource.createUserKeys()

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateKeyPair() = mUserStorageSource.createUserKeys()

    fun getUUIDsFromServer() = mUserStorageSource.getUUIDsFromServerOvr()
}


