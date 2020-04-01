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

    fun sendStatus(listOfEncounters: ArrayList<String?>) {
        val uuid = mUserStorageSource.getUserUUID()

        Log.d("Sending to Server: ", "-----------------------------------------\n" +
                " " + listOfEncounters.toString() + " " + uuid + " \n --------------------------------------------------")

        mUserStorageSource.sendClusterSubmission(listOfEncounters)
    }

}
