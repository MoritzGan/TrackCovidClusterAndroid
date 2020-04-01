package de.trackcovidcluster.data.network

import android.util.Log
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.entities.Request
import de.trackcovidcluster.data.entities.RequestClusters
import io.reactivex.Observable

class NetworkCall(private val trackCovidAPI: TrackCovidClusterAPI) {

    fun getStatus(body: Request): Observable<List<String>?> {
        return trackCovidAPI.getStatusFromAPI(body = body)
            .map { response ->
                response.answer.encounters
            }
    }

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

    fun sendBundle(userUUID: String?, clusters: ArrayList<String?>) : Observable<String> {
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
                        " Clusters : "      + response.answer.clusters.toString() + "\n" +
                        " Response : "      + response.toString() +
                        " \n            --------------------------------------------------")
                response.answer.clusters.toString()
            }
    }
}
