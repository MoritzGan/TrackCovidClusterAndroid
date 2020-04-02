package de.trackcovidcluster.main

import android.util.Log
import androidx.lifecycle.ViewModel
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import de.trackcovidcluster.source.UserStorageSource
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun isFirstTimeUser(): Boolean =
        !mUserStorageSource.isUserExisting()

    fun createUser() {
        mUserStorageSource.createUserKeys()
        getPublicKey()
    }

    fun generateKeyPair() = mUserStorageSource.createUserKeys()

    fun getUUIDsFromServer() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        val uuidsJson = JSONObject()

        networkSource.getUUIDs()
            .subscribeOn(Schedulers.io())
            .subscribe({ uuids ->

                uuidsJson.put("0", uuids?.get(0))
                uuidsJson.put("1", uuids?.get(1))
                uuidsJson.put("2", uuids?.get(2))
                uuidsJson.put("3", uuids?.get(3))
                uuidsJson.put("4", uuids?.get(4))

                mUserStorageSource.setUUIDsFromServer(uuidsJson = uuidsJson)
                Log.d("SERVER    ", "UUIDS FROM SERVER :" + uuidsJson)
            }, {
                Log.e("No internet", "No internet connection")
            })
    }

    private fun getPublicKey() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe({ publicKey ->
                mUserStorageSource.setPublicKey(publicKey = publicKey)
                Log.d("SERVER    ", "PUBLICKEY FROM SERVER :" + publicKey);
            }, {
                Log.e("No internet", "No internet connection")
            })
    }
}


