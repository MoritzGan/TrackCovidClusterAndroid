package de.trackcovidcluster.status

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.*
import de.trackcovidcluster.Constants.GET_STATUS_TAG
import de.trackcovidcluster.Constants.INFECTED
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import org.altbeacon.beacon.Beacon
import org.bouncycastle.crypto.digests.SHA3Digest
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class StatusViewModel @Inject constructor(
    private val mStatusStorageSource: IStatusStorageSource,
    private val mUserStorageSource: IUserStorageSource
) : ViewModel() {

    fun getStatus() {
        if (mStatusStorageSource.getStatus() != INFECTED) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val getStatusWorker =
                PeriodicWorkRequest.Builder(GetStatusWorker::class.java, 15, TimeUnit.MINUTES)
                    .addTag(GET_STATUS_TAG)
                    .setConstraints(constraints)
                    .build()
            WorkManager
                .getInstance()
                .enqueueUniquePeriodicWork(
                    GET_STATUS_TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    getStatusWorker
                )
        }
    }

    fun getStatusFromSource(): Int = mStatusStorageSource.getStatus()

    fun setMaybeInfected() {
        mStatusStorageSource.setMaybeInfectedStatus()
    }

    /**
     * Returns the actual public key of the user as a ByteArray.
     */

    @ExperimentalStdlibApi
    fun getPublicKeyByteArray(): ByteArray {
        val truncatedPublicKeyHash = ByteArray(8)

        mUserStorageSource.getUserUUID()?.let { publicKey ->

            val hash: ByteArray = doSha3(
                message = Base64.decode(
                    publicKey.toByteArray(),
                    Base64.NO_WRAP
                ),
                digest = SHA3Digest(256)
            )

            return hash
        }

        return truncatedPublicKeyHash
    }

    private fun doSha3(message: ByteArray, digest: SHA3Digest): ByteArray {
        val hash = ByteArray(digest.digestSize)
        if (message.isNotEmpty()) {
            digest.update(message, 0, message.size)
        }
        digest.doFinal(hash, 0)
        return hash
    }

    /**
     * Returns the public key of the server from the userStorageSource.
     */

    fun getServerPubKey(): String? {
        return mUserStorageSource.getUserPublicKey()
    }

    /**
     * TODO Check if needed anymore
     */

    fun getUUIDs(): JSONObject {
        val stringRep = mUserStorageSource.getUUIDsFromUser()

        var jsonRep = JSONObject()

        if (!stringRep.equals("")) jsonRep = JSONObject(stringRep!!)

        return jsonRep
    }
}
