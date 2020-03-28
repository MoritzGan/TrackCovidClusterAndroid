package de.trackcovidcluster.changeStatus

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.UserStorageSource
import io.reactivex.Observable
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    fun sendStatus() {
        val uUID = mUserStorageSource.getUserPublicKey()
        // TODO get cluster and call endpoint
    }

}
