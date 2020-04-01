package de.trackcovidcluster.changeStatus

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.NetworkCall
import de.trackcovidcluster.source.UserStorageSource
import io.reactivex.Observable
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun sendStatus(jsonString: String) {
        val uUID = mUserStorageSource.getUserUUID()

        val api: NetworkCall = NetworkCall(TrackCovidClusterAPI.create())
        Log.d("Sending to Server: ", "-----------------------------------------\n" +
                " " + jsonString + " \n --------------------------------------------------")
        api.sendBundle(jsonString)
    }

}
