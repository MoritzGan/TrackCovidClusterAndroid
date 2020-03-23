package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import de.trackcovidcluster.source.UserStorageSource
import javax.inject.Inject

class ChangeStatusViewModel  @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun sendStatus() {

        val uUID = mUserStorageSource.getUUID()
        // TODO get cluster and call endpoint
    }



}
