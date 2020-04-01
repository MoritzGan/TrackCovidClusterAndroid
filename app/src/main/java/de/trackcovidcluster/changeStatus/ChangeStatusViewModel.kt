package de.trackcovidcluster.changeStatus

import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun sendStatus(listOfEncounters: ArrayList<String?>) {
        val uuid = mUserStorageSource.getUserUUID()

        mUserStorageSource.sendClusterSubmission(listOfEncounters)
    }

}
