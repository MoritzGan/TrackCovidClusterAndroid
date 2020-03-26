package de.trackcovidcluster.source

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IUserStorageSource {

    companion object {
        private const val USER_ID = "USER_ID"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_ID, null).isNullOrEmpty()


    override fun createUser() {
        mSharedPreferences.edit().putString(USER_ID, UUID.randomUUID().toString()).apply()
    }

    override fun getUUID(): String? = mSharedPreferences.getString(USER_ID, null)
}
