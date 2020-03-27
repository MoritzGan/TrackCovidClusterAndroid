package de.trackcovidcluster.source

import android.content.SharedPreferences
import de.trackcovidcluster.server.ServerAdapter
import java.util.*
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences,
    private val mServerAdapter: ServerAdapter
) : IUserStorageSource {

    companion object {
        private const val USER_ID = "USER_ID"
        private const val PK_ID = "PUBLIC_KEY"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_ID, null).isNullOrEmpty()


    override fun createUser() {
        mSharedPreferences.edit().apply {
            putString(USER_ID, UUID.randomUUID().toString()).apply()
//            putString(PK_ID, mServerAdapter.publicKey).apply()
        }
    }

    override fun getUUID(): String? = mSharedPreferences.getString(USER_ID, null)
}
