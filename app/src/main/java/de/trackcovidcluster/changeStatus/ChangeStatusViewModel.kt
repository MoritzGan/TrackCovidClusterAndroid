package de.trackcovidcluster.changeStatus

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import de.trackcovidcluster.Constants.GET_STATUS_TAG
import de.trackcovidcluster.Constants.INFECTED
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import de.trackcovidcluster.source.StatusStorageSource
import de.trackcovidcluster.source.UserStorageSource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource,
    private val mStatusStorageSource: StatusStorageSource
) : ViewModel() {

    fun sendStatus(listOfEncounters: ArrayList<String?>) {
        mStatusStorageSource.setStatus(INFECTED)

        sendClustersToServer(listOfEncounters)
    }

    fun stopWorker() =
        WorkManager.getInstance().cancelAllWorkByTag(GET_STATUS_TAG)

    private fun sendClustersToServer(clusters: List<String?>) {

        val networkSource = NetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())
        val uuid: String? = mUserStorageSource.getUserUUID()

        if (clusters.isNotEmpty()) {
            networkSource.sendBundle(uuid, clusters)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.i("Server Success", "Connected and sent POST.")
                }, {
                    Log.e("No internet", "No internet connection$it")
                })
        }
    }
}
