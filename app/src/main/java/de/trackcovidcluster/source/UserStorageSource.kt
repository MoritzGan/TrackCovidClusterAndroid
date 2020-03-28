package de.trackcovidcluster.source

import android.content.SharedPreferences
import android.util.Log
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import io.reactivex.schedulers.Schedulers
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


    override fun createUserKeys() {
        val seed: ByteArray = Random().randomBytes(SodiumConstants.SECRETKEY_BYTES)

        val encryptionKeyPair = KeyPair(seed)
        val encryptionPublicKey: ByteArray = encryptionKeyPair.publicKey.toBytes()
        val encryptionPrivateKey: ByteArray = encryptionKeyPair.privateKey.toBytes()

        mSharedPreferences.edit().apply {
            putString(USER_PRIVATE_KEY_ID, encryptionPrivateKey.toString())
            putString(USER_PUBLIC_KEY_ID, encryptionPublicKey.toString())
        }.apply()

        Log.d(
            "FIRST TIME USER:", "\n" + "GENERATED KEYPAIR \n" +
                    encryptionPrivateKey.toString()
        )
    }

    override fun getUUID(): String? = mSharedPreferences.getString(USER_ID, null)

    override fun getUserPublicKey() =  mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null)

    private fun getPublicKey() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe { publicKey ->
                mSharedPreferences.edit().putString(PK_ID, publicKey).apply()
            }
    }
}
