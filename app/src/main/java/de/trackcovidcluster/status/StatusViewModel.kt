package de.trackcovidcluster.status

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import io.reactivex.Observable
import org.altbeacon.beacon.Beacon
import java.security.MessageDigest
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

    fun getBeacon(): Beacon? {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

        mUserStorageSource.getUserPublicKey()?.let { publicKey ->
            val hashBytes = digest.digest(
                publicKey.toByteArray()
            )

            val sha3_256hex: String = hashBytes
            Log.d("HASH OF PUBKEY", " \n $sha3256Hex")


            return Beacon.Builder()
                .setId1(mUserStorageSource.getUUID())
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x004c)
                .setTxPower(-59)
                .build()
        }
        return null
    }

    // TODO: Implement stop work
//    fun stopTrackLocation() {
//        WorkManager.getInstance().cancelAllWorkByTag(LOCATION_WORK_TAG)
//    }
}
