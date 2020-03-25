package de.trackcovidcluster.worker

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.data.entities.Request
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.StatusNetworkCall
import io.reactivex.Single
import javax.inject.Inject

class GetStatusWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters
) : RxWorker(appContext, workerParams) {

    override fun createWork(): Single<Result> {
        val networkSource = StatusNetworkCall(trackCovidAPI = TrackCovidClusterAPI.create())

        return networkSource.getStatus(
            body = Request(
                command = "StatePoll",
                uuid = "CJnw5cqBJ153OTIka2tqDKWoOvBKqis2M+7zEj07WTM="
            )
        ).firstOrError()
            .flatMap { result ->
                if(!result.encounters.isNullOrEmpty()){
                    // infected send push notification and change status
                }
                Single.just(Result.success())
            }
            .onErrorResumeNext {
                Log.e("nooo", it.message.toString())
                Single.just(Result.failure())
            }
    }
}
