package de.trackcovidcluster.status

import android.app.Application
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.worker.GetStatusWorker
import io.reactivex.Observable
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import java.util.*
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

    // TODO: Implement stop work
//    fun stopTrackLocation() {
//        WorkManager.getInstance().cancelAllWorkByTag(LOCATION_WORK_TAG)
//    }
}
