package de.trackcovidcluster.status

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import de.trackcovidcluster.source.UserStorageSource
import io.reactivex.Observable
import javax.inject.Inject

class ChangeStatusViewModel @Inject constructor(
    private val mUserStorageSource: UserStorageSource
) : ViewModel() {

    // region Members
    private val mStatus = PublishRelay.create<Int>()
    // endregion

    // region Observables
    fun onChangeStatus(): Observable<Int> = mStatus
    // endregion

    fun sendStatus() {
        val uUID = mUserStorageSource.getUUID()
        // TODO get cluster and call endpoint
    }

    fun changeStatus(status: Int) {
        mStatus.accept(status)
    }


}
