package de.trackcovidcluster.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import org.libsodium.jni.SodiumConstants
import org.libsodium.jni.crypto.Random
import org.libsodium.jni.keys.KeyPair
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun isFirstTimeUser(): Boolean =
        !mUserStorageSource.isUserExisting()

    fun createUser() = mUserStorageSource.createUser()

    fun generateKeyPair() = mUserStorageSource.createUserKeys()
}


