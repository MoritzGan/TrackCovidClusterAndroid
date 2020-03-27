package de.trackcovidcluster.source

import android.content.SharedPreferences
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IUserStorageSource {

    companion object {
        private const val USER_ID = "USER_ID"
        private const val PK_ID = "PUBLIC_KEY"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_ID, null).isNullOrEmpty()


    override fun createUser() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe { publicKey ->
                mSharedPreferences.edit().putString(PK_ID, publicKey).apply()
            }

        mSharedPreferences.edit()
            .putString(USER_ID, UUID.randomUUID().toString()).apply()
    }

    override fun getUUID(): String? = mSharedPreferences.getString(USER_ID, null)
}
