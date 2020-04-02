package de.trackcovidcluster.data.network

import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.entities.Request
import de.trackcovidcluster.data.entities.RequestClusters
import io.reactivex.Observable
import java.nio.charset.Charset

class NetworkCall(private val trackCovidAPI: TrackCovidClusterAPI) {

    fun getStatus(body: Request): Observable<List<String>?> {
        return trackCovidAPI.getStatusFromAPI(body = body)
            .map { response ->
                response.answer.encounters
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPublicKey(): Observable<String> {
        val body = Request(
            command = "RequestServerPubKey"
        )
        return trackCovidAPI.getPublicKey(body = body)
            .map { response ->
                response.answer.publicKey
            }
    }

    fun getUUIDs() : Observable<List<String>?> {
        val body = Request(
            command = "UUIDPoll"
        )
        return trackCovidAPI.getUUIDs(body = body)
            .map { response ->
                response.answer.uuids
            }
    }

    // TODO: Debug the sending process. Does not work

    fun sendBundle(userUUID: String?, clusters: List<String?>) : Observable<String> {
        val body = RequestClusters(
            command = "ClusterSubmission",
            clusters = clusters,
            uuid = userUUID
        )

        return trackCovidAPI.sendBundle(body = body)
            .map { response ->
                Log.d("Cluster Submission: ",
                    "-----------------------------------------\n" +
                        " Server Answer: "  + response.answer.toString() + "\n" +
                        " Clusters   : "      + response.answer.encounters.toString() + "\n" +
                        " Response   : "      + response.toString() +
                        " Body o Req : "      + body.toString() +
                        " \n            --------------------------------------------------")

                response.answer.status.toString()
            }
    }
}
