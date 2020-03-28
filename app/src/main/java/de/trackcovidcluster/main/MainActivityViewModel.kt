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

    fun generateKeyPair(context: Context) {
        val sharedPreference =  context.getSharedPreferences("KEYSET",Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreference.edit()
        val seed: ByteArray = Random().randomBytes(SodiumConstants.SECRETKEY_BYTES)

        val encryptionKeyPair = KeyPair(seed)
        val encryptionPublicKey: ByteArray = encryptionKeyPair.publicKey.toBytes()
        val encryptionPrivateKey: ByteArray = encryptionKeyPair.privateKey.toBytes()

        editor.putString("PrivateKey", encryptionPrivateKey.toString())
        editor.putString("PublicKey", encryptionPublicKey.toString())
        editor.commit()

        Log.d("FIRST TIME USER:", "\n" + "GENERATED KEYPAIR \n" +
                encryptionPrivateKey.toString())
    }
}


