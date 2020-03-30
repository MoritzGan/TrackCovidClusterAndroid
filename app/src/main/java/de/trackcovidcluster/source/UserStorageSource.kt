package de.trackcovidcluster.source

import android.content.SharedPreferences
import android.util.Base64
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import io.reactivex.schedulers.Schedulers
import org.libsodium.jni.SodiumConstants
import org.libsodium.jni.crypto.Random
import org.libsodium.jni.keys.KeyPair
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IUserStorageSource {

    companion object {
        private const val SERVER_PUBLIC_KEY_ID = "SERVER_PUBLIC_KEY"
        private const val USER_PUBLIC_KEY_ID = "PUBLIC_KEY"
        private const val USER_PRIVATE_KEY_ID = "SERVER_PRIVATE_KEY"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null).isNullOrEmpty()


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
        getServerPublicKey()
    }

    override fun getUserPublicKey() = mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null)

    private fun getServerPublicKey() {
        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        networkSource.getPublicKey()
            .subscribeOn(Schedulers.io())
            .subscribe { publicKey ->
                mSharedPreferences.edit().putString(SERVER_PUBLIC_KEY_ID, publicKey).apply()
            }
    }
}
