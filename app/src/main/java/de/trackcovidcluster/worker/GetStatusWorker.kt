package de.trackcovidcluster.worker

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import de.trackcovidcluster.data.entities.Request
import de.trackcovidcluster.R
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.network.StatusNetworkCall
import de.trackcovidcluster.di.IChildRxWorkerFactory
import de.trackcovidcluster.source.IUserStorageSource
import de.trackcovidcluster.status.Constants
import de.trackcovidcluster.status.Constants.PUSH_NOTIFICATION_CHANNEL
import de.trackcovidcluster.status.StatusActivity
import io.reactivex.Single
import javax.inject.Inject

class GetStatusWorker @Inject constructor(
    mContext: Context,
    mWorkerParams: WorkerParameters,
    private val mUserStorageSource: IUserStorageSource
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
                    if (!isForeground()) {
                        sendPushNotification()
                    }
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

    private fun sendPushNotification() {
        createNotificationChannel()
        val statusActivity = Intent(applicationContext, StatusActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(
                Constants.STATUS_API_KEY,
                Constants.MAYBE_INFECTED
            )
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, statusActivity, 0)

        val builder =
            NotificationCompat.Builder(applicationContext, PUSH_NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.maybe_infected_small)
                .setContentTitle(applicationContext.getString(R.string.notification_title))
                .setContentText(applicationContext.getString(R.string.notification_body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TrackCovidCluster - Infection Risk"
            val descriptionText = "Notify in case you were in cotact with someone who is infected"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(PUSH_NOTIFICATION_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun isForeground(): Boolean {
        val manager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfo: List<ActivityManager.RunningTaskInfo> = manager.getRunningTasks(1)
        val componentInfo = runningTaskInfo[0].topActivity
        return componentInfo?.packageName.equals("de.trackcovidcluster")
    }

    internal class Factory @Inject constructor(
        private val mUserStorageSource: IUserStorageSource
    ) : IChildRxWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): RxWorker =
            GetStatusWorker(
                mContext = context,
                mWorkerParams = workerParameters,
                mUserStorageSource = mUserStorageSource
            )
    }
}
