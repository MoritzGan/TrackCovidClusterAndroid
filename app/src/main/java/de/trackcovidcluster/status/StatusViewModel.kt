package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import androidx.work.*
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.database.DatabaseHelper
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StatusViewModel @Inject constructor(
    private val mStatusStorageSource: IStatusStorageSource
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

    fun getStatusFromSource() : Int = mStatusStorageSource.getStatus()
    fun setMaybeInfected() {
        mStatusStorageSource.setMaybeInfectedStatus()
        onGetStatus()
    }

    fun test() {
        // TODO Create Keypair wit lipsodium
        // byte[] secret_key = new byte[Box.SECRETKEYBYTES];
        // byte[] public_key = new byte[Box.PUBLICKEYBYTES];
        // Box.keypair(public_key, secret_key);

        // TODO encrypt
        // Box.seal(
        //    ciphertextByteArray, // Output goes here
        //    plaintextByteArray,  // Your message
        //    public_key
        // );

        // TODO Hash the public key
        // MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // byte[] hash = digest.digest(public_key.getBytes("UTF-8"));
    }

    // TODO: Implement stop work
//    fun stopTrackLocation() {
//        WorkManager.getInstance().cancelAllWorkByTag(LOCATION_WORK_TAG)
//    }
}
