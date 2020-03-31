package de.trackcovidcluster.source

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import io.reactivex.schedulers.Schedulers
import org.bouncycastle.util.StringList
import org.json.JSONObject
import org.libsodium.jni.Sodium
import org.libsodium.jni.SodiumConstants
import org.libsodium.jni.crypto.Random
import org.libsodium.jni.keys.KeyPair
import java.util.*
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IUserStorageSource {

    companion object {
        private const val USER_ID = "USER_ID"
        private const val PK_ID = "SERVER_PUBLIC_KEY"
        private const val UUID_LIST = "UUIDS"
        private const val USER_PUBLIC_KEY_ID = "PUBLIC_KEY"
        private const val USER_PRIVATE_KEY_ID = "SERVER_PRIVATE_KEY"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_ID, null).isNullOrEmpty()


    override fun createUser() {
        mSharedPreferences.edit()
            .putString(USER_ID, UUID.randomUUID().toString()).apply()
        getPublicKey()
    }

    override fun getUUIDsFromServerOvr() {
        getUUIDsFromServer()
    }

    override fun getUUIDsFromUser(): String? {
        return mSharedPreferences.getString(UUID_LIST, "")
    }

    override fun createUserKeys() {
        val seed: ByteArray = Random().randomBytes(SodiumConstants.SECRETKEY_BYTES)

        val encryptionKeyPair = KeyPair(seed)
        val encryptionPublicKey: ByteArray = encryptionKeyPair.publicKey.toBytes()
        val encryptionPrivateKey: ByteArray = encryptionKeyPair.privateKey.toBytes()

        mSharedPreferences.edit().apply {
            putString(
                USER_PRIVATE_KEY_ID,
                Base64.encodeToString(encryptionPrivateKey, Base64.DEFAULT).substring(0, 44)
            )
            putString(
                USER_PUBLIC_KEY_ID,
                Base64.encodeToString(encryptionPublicKey, Base64.DEFAULT).substring(0, 44)
            )
        }.apply()

        Log.d(
            "FIRST TIME USER:", "\n" + "GENERATED KEYPAIR \n" +
                    encryptionPrivateKey.toString()
        )
    }

    override fun getUserPublicKey() = mSharedPreferences.getString(PK_ID, null)

    private fun getPublicKey() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe { publicKey ->
                mSharedPreferences.edit().putString(PK_ID, publicKey).apply()
                Log.d("SERVER    ", "PUBLICKEY FROM SERVER :" + publicKey);
            }
    }

    private fun getUUIDsFromServer(): String? {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        var uuidsJson = JSONObject()

        networkSource.getUUIDs()
            .subscribeOn(Schedulers.io())
            .subscribe { uuids ->

                uuidsJson.put("0", uuids?.get(0))
                uuidsJson.put("1", uuids?.get(1))
                uuidsJson.put("2", uuids?.get(2))
                uuidsJson.put("3", uuids?.get(3))
                uuidsJson.put("4", uuids?.get(4))

                mSharedPreferences.edit().putString(UUID_LIST, uuidsJson.toString()).apply()
                Log.d("SERVER    ", "UUIDS FROM SERVER :" + uuidsJson)
            }

        return uuidsJson.toString()
    }
}
