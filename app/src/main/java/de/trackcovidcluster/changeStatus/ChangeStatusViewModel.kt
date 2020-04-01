package de.trackcovidcluster.changeStatus

import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.StatusStorageSource
import de.trackcovidcluster.source.UserStorageSource
import de.trackcovidcluster.status.Constants.INFECTED
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource,
    private val mStatusStorageSource: StatusStorageSource
) : ViewModel() {

    fun sendStatus(listOfEncounters: ArrayList<String?>) {
        mStatusStorageSource.setStatus(INFECTED)

        mUserStorageSource.sendClusterSubmission(listOfEncounters.toList())
    }

}
