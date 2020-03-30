package de.trackcovidcluster.source

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import io.reactivex.schedulers.Schedulers
import org.bouncycastle.util.StringList
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
        getUUIDsFromServer()
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

    override fun getUserPublicKey() = mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null)

    private fun getPublicKey() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe { publicKey ->
                mSharedPreferences.edit().putString(PK_ID, publicKey).apply()
                Log.d("SERVER    ", "PUBLICKEY FROM SERVER :" + publicKey);
            }
    }

    private fun getUUIDsFromServer() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        var concattedUuids: String = ""
        networkSource.getUUIDs()
            .subscribeOn(Schedulers.io())
            .subscribe { uuids ->
                if (uuids != null) {
                    for (x :String in uuids) {
                        concattedUuids += x
                    }
                }
                mSharedPreferences.edit().putString(UUID_LIST, concattedUuids).apply()
                Log.d("SERVER    ", "UUIDS FROM SERVER :" + uuids);
            }
    }
}
