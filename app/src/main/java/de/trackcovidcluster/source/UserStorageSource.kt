package de.trackcovidcluster.source

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import org.json.JSONObject
import org.libsodium.jni.SodiumConstants
import org.libsodium.jni.crypto.Random
import org.libsodium.jni.keys.KeyPair
import javax.inject.Inject

class UserStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IUserStorageSource {

    companion object {
        private const val SERVER_PUBLIC_KEY = "SERVER_PUBLIC_KEY"
        private const val UUID_LIST = "UUIDS"
        private const val USER_PUBLIC_KEY_ID = "PUBLIC_KEY"
        private const val USER_PRIVATE_KEY_ID = "USER_PRIVATE_KEY_ID"
    }

    override fun isUserExisting(): Boolean =
        !mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null).isNullOrEmpty()

    override fun getBase64UserPublicKey(): String? =
        mSharedPreferences.getString(USER_PUBLIC_KEY_ID, null)

    override fun setUUIDsFromServer(uuidsJson: JSONObject) =
        mSharedPreferences.edit().putString(UUID_LIST, uuidsJson.toString()).apply()

    override fun getUUIDsFromSharedPreferences(): String? =
        mSharedPreferences.getString(UUID_LIST, "")

    override fun generateAndSaveKeyPair() {
        val seed: ByteArray = Random().randomBytes(SodiumConstants.SECRETKEY_BYTES)

        val encryptionKeyPair = KeyPair(seed)
        val encryptionPublicKey: ByteArray = encryptionKeyPair.publicKey.toBytes()
        val encryptionPrivateKey: ByteArray = encryptionKeyPair.privateKey.toBytes()

        mSharedPreferences.edit().apply {
            putString(
                USER_PRIVATE_KEY_ID,
                Base64.encodeToString(encryptionPrivateKey, Base64.NO_WRAP)
            )
            putString(
                USER_PUBLIC_KEY_ID,
                Base64.encodeToString(encryptionPublicKey, Base64.NO_WRAP)
            )
        }.apply()
    }

    override fun getServerPublicKey() = mSharedPreferences.getString(SERVER_PUBLIC_KEY, null)

    override fun setPublicKey(publicKey: String) =
        mSharedPreferences.edit().putString(SERVER_PUBLIC_KEY, publicKey).apply()

}
