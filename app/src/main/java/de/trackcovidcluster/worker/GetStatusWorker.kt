package de.trackcovidcluster.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.data.entities.Request
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.StatusNetworkCall
import de.trackcovidcluster.di.IChildRxWorkerFactory
import de.trackcovidcluster.source.IStatusStorageSource
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.status.Constants
import io.reactivex.Single
import javax.inject.Inject

class GetStatusWorker @Inject constructor(
    mContext: Context,
    mWorkerParams: WorkerParameters,
    private val mUserStorageSource: IUserStorageSource,
    private val mStatusStorageSource: IStatusStorageSource
) : RxWorker(mContext, mWorkerParams) {

    override fun createWork(): Single<Result> {
        val networkSource = StatusNetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())

        return networkSource.getStatus(
            body = Request(
                command = "StatePoll",
                uuid = mUserStorageSource.getUUID().toString()
            )
        ).firstOrError()
            .flatMap { result ->
                if (!result.encounters.isNullOrEmpty()) {
                    // infected send push notification and change status

                    applicationContext.sendBroadcast(
                        Intent(
                            "android.intent.action.MAYBE_INFECTED"
                        ).putExtra(
                            Constants.STATUS_API_KEY,
                            Constants.MAYBE_INFECTED
                        )
                    )
                }
                Single.just(Result.success())
            }
            .onErrorResumeNext {
                Log.e("error", it.message.toString())
                Single.just(Result.failure())
            }
    }

    internal class Factory @Inject constructor(
        private val mUserStorageSource: IUserStorageSource,
        private val mStatusStorageSource: IStatusStorageSource
    ) : IChildRxWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): RxWorker =
            GetStatusWorker(
                mContext = context,
                mWorkerParams = workerParameters,
                mUserStorageSource = mUserStorageSource,
                mStatusStorageSource = mStatusStorageSource
            )
    }
}
