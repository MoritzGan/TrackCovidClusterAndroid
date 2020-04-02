package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import androidx.work.*
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import org.altbeacon.beacon.Beacon
import org.bouncycastle.jcajce.provider.digest.SHA3
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StatusViewModel @Inject constructor(
    private val mStatusStorageSource: IStatusStorageSource,
    private val mUserStorageSource: IUserStorageSource
) : ViewModel() {

    companion object {
        private const val GET_STATUS_TAG = "GET_STATUS"
    }

    fun getStatus() {
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

    fun getStatusFromSource(): Int = mStatusStorageSource.getStatus()

    fun setMaybeInfected() {
        mStatusStorageSource.setMaybeInfectedStatus()
    }

    fun getBeacon(uuid: String, major: String, minor: String): Beacon? {

        return Beacon.Builder()
            .setId1(uuid)
            .setId2(major)
            .setId3(minor)
            .setManufacturer(0x004c)
            .setTxPower(-59)
            .build()
    }

    fun getPublicKeyByteArray(): ByteArray {
        val testArray = ByteArray(16)

        mUserStorageSource.getUserUUID()?.let { publicKey ->

            val digestSHA3: SHA3.DigestSHA3 = SHA3.Digest256()
            val digest: ByteArray = digestSHA3.digest(publicKey.toByteArray())

            for (i in 0..15) {
                testArray[i] = digest[i]
            }

            return testArray
        }

        return testArray
    }

    fun getUUIDs(): JSONObject {
        val stringRep = mUserStorageSource.getUUIDsFromUser()

        var jsonRep = JSONObject()

        if (!stringRep.equals("")) jsonRep = JSONObject(stringRep!!)

        return jsonRep
    }

    fun getServerPubKey(): String? {
        return mUserStorageSource.getUserPublicKey()
    }
}
