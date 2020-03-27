package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import androidx.work.*
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import io.reactivex.Observable
import org.altbeacon.beacon.Beacon
import org.libsodium.jni.SodiumConstants
import org.libsodium.jni.crypto.Box
import org.libsodium.jni.crypto.Random
import java.security.KeyPair
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

        val private = Random().randomBytes(SodiumConstants.SECRETKEY_BYTES)

        val public = Random().randomBytes(SodiumConstants.PUBLICKEY_BYTES)

        val encryptionKeyPair = KeyPair(public, private)

        // TODO encrypt
        Box.seal(
            ciphertextByteArray, // Output goes here
            plaintextByteArray,  // Your message
            public_key
        );

        return Beacon.Builder()
            .setId1(mUserStorageSource.getUUID())
            .setId2("1")
            .setId3("2")
            .setManufacturer(0x004c)
            .setTxPower(-59)
            .build()
    }

    // TODO: Implement stop work
//    fun stopTrackLocation() {
//        WorkManager.getInstance().cancelAllWorkByTag(LOCATION_WORK_TAG)
//    }
}
