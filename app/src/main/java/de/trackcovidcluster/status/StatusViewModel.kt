package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import androidx.work.*
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import io.reactivex.Observable
import org.altbeacon.beacon.Beacon
import org.bouncycastle.jcajce.provider.digest.SHA3
import org.json.JSONObject
import java.math.BigInteger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StatusViewModel @Inject constructor(
    private val mStatusStorageSource: IStatusStorageSource,
    private val mUserStorageSource: IUserStorageSource
) : ViewModel() {

    companion object {
        private const val GET_STATUS_TAG = "GET_STATUS"
    }

    // region Members
    private val mStatus = PublishRelay.create<Int>()
    // endregion

    // region Observables
    fun onGetStatus(): Observable<Int> = mStatus
    // endregion

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
        onGetStatus()
    }

    fun getBeacon(uuid: String, major: String, minor: String): Beacon? {

        return Beacon.Builder()
            .setBluetoothAddress("6BEC8F04BA0C") // TODO does not change the mac
            .setBluetoothName("Testing")         // TODO Also not readable
            .setId1(uuid)
            .setId2(major)
            .setId3(minor)
            .setManufacturer(0x004c)
            .setTxPower(-59)
            .build()
    }

    fun getPublicKeyInInt(): BigInteger {

        mUserStorageSource.getUserPublicKey()?.let { publicKey ->

            val digestSHA3: SHA3.DigestSHA3 = SHA3.Digest256()
            val digest = digestSHA3.digest(publicKey.toByteArray())

            var hex = ""
            digest.map {
                hex += String.format("%02X", it)
            }

            val result: String = hex.substring(32)
            return BigInteger(result, 16)
        }

        return BigInteger("0")
    }

    fun getUUIDs(): JSONObject {
        val stringRep = mUserStorageSource.getUUIDsFromUser()

        var jsonRep = JSONObject()

        if (!stringRep.equals("")) jsonRep = JSONObject(stringRep!!)

        return jsonRep
    }
}
